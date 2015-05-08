/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

import org.junit.experimental.categories.Categories;

import com.searcheveryaspect.backend.shared.*;

/**
 * @author dajmmannen
 *
 */
public class WordCountCategoriser implements Categoriser {


	@Override
	public String[] categorise(ESDocument doc) 
	{
		Category[] categories = Category.values();
		
		String[] categoriesString = new String[categories.length];
		int[] wordcounts;
		String[] splitText;
		
		for(int i = 0; i < categories.length; i++)
		{
			categoriesString[i] = categories[i].toString();
		}
		
		wordcounts = new int[categoriesString.length];
		
		splitText = doc.text.split(" ");
		
		for(String s : splitText)
			for(int i = 0; i < categoriesString.length; i++)
				if(s.equals(categoriesString))
					wordcounts[i]++;
		
		int max = 0;
		int maxIndex = 0;
		for(int i = 0; i < wordcounts.length; i++)
		{
			if(wordcounts[i] > max)
			{
				max = wordcounts[i];
				maxIndex = i;
			}
		}
		
		//If no word is found we have not been able to
		//define a category
		if(max == 0)
			return null;
		return new String[]{categoriesString[maxIndex]};
		
		
	}

}
