package com.searcheveryaspect.backend;


public class GovClient 
{
	public void fetchDocs(GovFetchRequest request) throws Exception
	{
		String json = URLConnectionReader.getText(request.toString());
		
		System.out.println(request.toString());
		System.out.println(json);
		
	}
}
