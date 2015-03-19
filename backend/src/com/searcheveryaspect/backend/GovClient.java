package com.searcheveryaspect.backend;

import java.util.ArrayList;

import com.google.gson.Gson;


public class GovClient 
{
	public void fetchDocs(GovFetchRequest request) throws Exception
	{
		String json = URLConnectionReader.getText(request.toString());
		System.out.println(json);
		
	}
	
	public void fetchAllDocs() throws Exception
	{
		GovFetchRequest request = new GovFetchRequest("", "", new Period(new GovDate(1900, 1, 1), new GovDate(2020, 1, 1)), "", "", "", "", "", new ArrayList<String>());
		String json = URLConnectionReader.getText(request.toString());
	}
	
	public GovDocumentList gsonHandler(String json)
	{
		/*
		 * Does not work yet
		Gson gson = new Gson();
		GovDocumentList gdl = gson.fromJson(json, GovDocumentList.class);
		
		return gdl;
		*/
		
		return null;
	}
}
