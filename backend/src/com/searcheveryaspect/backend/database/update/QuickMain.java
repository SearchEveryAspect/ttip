/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.elasticsearch.common.lang3.ArrayUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import com.searcheveryaspect.backend.shared.Category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author dajmmannen
 *
 */
public class QuickMain 
{
	public static void main(String[] args)
	{
		  DateTime startDate = new DateTime(2010, 1, 1, 1, 1);
		  DateTime endDate = new DateTime(2011, 1, 1, 1, 1);
		  
		  System.out.println("Initialising builder");
		  ESDocumentBuilder.initBuilder();
		  System.out.println("Done.");
		  
		  Interval interval = new Interval(startDate, endDate);
		  System.out.println("Creting new request");
		  GovFetchRequest request = new GovFetchRequest("skatt", "", interval, "","", "", "", "", new ArrayList<String>());
		  System.out.println("Done.");
		  
		  ArrayList<GovDocumentList> listList = null;
		  ArrayList<GovDocumentLite> list;
		  ArrayList<ESDocument> esList = new ArrayList<>();
		  
		  
		  try 
		  {
			  System.out.println("Fetching documents");
			  listList = GovClient.fetchDocs(request);
			  System.out.println("Done.");
		  } 
		  catch (Exception e) 
		  {
			e.printStackTrace();
		  }
		  
		  System.out.println("Converting documents");
		  list = GovClient.documentConverter(listList);
		  System.out.println("Done.");
		  
		  System.out.println("Adding esDocuments");
		  for(GovDocumentLite d : list)
		  {
			  esList.add(ESDocumentBuilder.createESDocument(d));
		  }
		  System.out.println("Done");
		  
		  for(ESDocument d : esList)
		  {
			  System.out.println("Title :" + d.getTitle());
			  System.out.print("Categories: ");
			  for(String s : d.getCategory())
			  {
				  System.out.println(s + " ");
			  }
		  }

	}
	
}
