package com.searcheveryaspect.backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;


public class GovClient 
{
	public ArrayList<GovDocumentList> fetchDocs(GovFetchRequest request) throws Exception
	{
		GovDocumentList fetched;
		String json;
		Gson gson = new Gson();
		ArrayList<GovDocumentList> result;
		
		//Gets the initial request
		json = URLConnectionReader.getText(request.toString());
		gson = new Gson();
		result = new ArrayList<GovDocumentList>();
		fetched = gson.fromJson(json, GovSearchResult.class).dokumentlista;
		
		result.add(fetched);
		
		if(fetched.warning != null)
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("ERROR WARNING FOUND: " + fetched.warning);
			System.out.print("Query might fail, continue? [Y/N] ");
			if( Character.toLowerCase(((char)in.read())) != 'y' )
				return result;
		}
		
		int i = 1;
		//Checks if there are more pages found
		while(fetched.existsNextPage())
		{
			System.out.println("Fetching page " + i + " out of " + fetched.sidor);
			json = URLConnectionReader.getText(fetched.nextPage());
			System.out.println("Parsing page " + i + " out of " + fetched.sidor);
			fetched = gson.fromJson(json, GovSearchResult.class).dokumentlista;
			//Adds extra pages
			result.add(fetched);
			i++;
		}
		
		return result;
		
	}
	
	public ArrayList<GovDocumentList> fetchAllDocs() throws Exception
	{
		Calendar d = Calendar.getInstance();
		return fetchDocs(new GovFetchRequest("", "", new Period(new GovDate(1900, 1, 1),new GovDate(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH))), "", "", "", "", "", new ArrayList<String>()));
	}
}
