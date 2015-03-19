package com.searcheveryaspect.backend;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;


public class GovClient 
{
	public GovDocumentList fetchDocs(GovFetchRequest request) throws Exception
	{
		String json = URLConnectionReader.getText(request.toString());
		
		Gson gson = new Gson();
		
		
		System.out.println(json);
		
		return gson.fromJson(json, GovSearchResult.class).dokumentlista;
		
	}
	
	public GovDocumentList fetchAllDocs() throws Exception
	{
		Calendar d = Calendar.getInstance();
		GovFetchRequest request = new GovFetchRequest("", "", new Period(new GovDate(1900, 1, 1),new GovDate(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH))), "", "", "", "", "", new ArrayList<String>());
		String json = URLConnectionReader.getText(request.toString());
		
		Gson gson = new Gson();
		
		return gson.fromJson(json, GovSearchResult.class).dokumentlista;
	}
}
