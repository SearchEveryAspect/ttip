/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

/**
 * @author dajmmannen
 *
 */
public class WordPair 
{
	String word;
	int points;
	
	public WordPair(String word, int points)
	{
		this.word = word;
		this.points = points;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public int getPoints()
	{
		return points;
	}
}
