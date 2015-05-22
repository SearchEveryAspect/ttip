/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.elasticsearch.common.lang3.ArrayUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.searcheveryaspect.backend.shared.*;

/**
 * @author lundblom
 * Identifies categories with word matching.
 */
public class WordCountCategoriser implements Categoriser {

	//If this number times the secondMax value is larger or equal
	//to the second category, it is accepted.
	private final float SECOND_CATEGORY_PERCENTAGE = 1.25f;

	@Override
	public String[] categorise(ESDocument doc) 
	{
		
		File fXmlFile = new File("categories.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try 
		{
			dBuilder = dbFactory.newDocumentBuilder();
		} 
		
		catch (ParserConfigurationException e) 
		{
			System.out.println("Couldn't init document builder in " + this.getClass().toString());
			e.printStackTrace();
		}
		
		Document xmlDoc = null;
		try 
		{
			xmlDoc = dBuilder.parse(fXmlFile);
		} 
		catch (SAXException | IOException e) 
		{
			System.out.println("Couldn't parse categories in " + this.getClass().toString());
			e.printStackTrace();
		}
		
	 
		xmlDoc.getDocumentElement().normalize();
	 
		NodeList nList = xmlDoc.getElementsByTagName("category");
		
		int[] categoryPoints = new int[nList.getLength()];
		
		ArrayList<ArrayList<WordPair>> categoriesAndWords = new ArrayList<ArrayList<WordPair>>();
		
		for(int i = 0; i < nList.getLength(); i++)
		{
			ArrayList<WordPair> l = new ArrayList<>();
			categoriesAndWords.add(l);
		}
		
		for(int i = 0; i < nList.getLength(); i++)
		{
			NodeList subNList = nList.item(i).getChildNodes();
			for(int j = 0; j < subNList.getLength(); j++)
			{
				Node n = subNList.item(j);
				if(!n.getTextContent().equals("") && n.getAttributes() != null)
				{
					WordPair wp = new WordPair(n.getTextContent().replaceAll("\\s", ""), 
							Integer.parseInt(n.getAttributes().item(0).getNodeValue()));
					categoriesAndWords.get(i).add(wp);
				}
			}
			
		}
		
		String[] splitText = null;

		
		if(doc.text == null)
			splitText = ArrayUtils.addAll(doc.getTitle().split(" ")); 
		else
			splitText = doc.text.split(" ");
		
		wordLoop:
		for(String s : splitText)
		{
			for(int i = 0; i < categoriesAndWords.size(); i++)
			{
				for(int j = 0; j < categoriesAndWords.get(i).size(); j++)
				{
					WordPair p = categoriesAndWords.get(i).get(j);
					if(s.toLowerCase().matches(".*" + p.getWord() + ".*"))
					{
						categoryPoints[i] += p.getPoints();
						continue wordLoop;
					}
				}
			}
		}
		
		int firstMax = 0;
		int firstMaxIndex = 0;
		
		int secondMax = 0;
		int secondMaxIndex = 0;
		for(int i = 0; i < categoryPoints.length; i++)
		{
			
			if(categoryPoints[i] > firstMax)
			{
				secondMax = firstMax;
				secondMaxIndex = firstMaxIndex;
				firstMax = categoryPoints[i];
				firstMaxIndex = i;
				continue;
			}
			if(categoryPoints[i] > secondMax)
			{
				secondMax = categoryPoints[i];
				secondMaxIndex = i;
			}
		}
		
		//If no word is found we have not been able to
		//define a category
		if(firstMax == 0)
			return new String[]{Category.UNKNOWN.toString()};
		
		if((float)secondMax * SECOND_CATEGORY_PERCENTAGE >= (float)firstMax)
			return new String[]{nList.item(firstMaxIndex).getAttributes().item(0).getTextContent(), nList.item(secondMaxIndex).getAttributes().item(0).getTextContent()};
		
		return new String[]{nList.item(firstMaxIndex).getAttributes().item(0).getTextContent()};
		
		
	}
}
