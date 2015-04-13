package com.searcheveryaspect.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;


public class GovClient 
{
	
	public static final int TRAFFAR_LIMIT = 5000;
	
	public ArrayList<GovDocumentList> fetchDocs(GovFetchRequest request) throws Exception
	{
		GovDocumentList fetched;
		String json = null;
		Gson gson = new Gson();
		ArrayList<GovDocumentList> result;
		
		//Gets the initial request
		try {
			json = URLConnectionReader.getText(request.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		gson = new Gson();
		result = new ArrayList<GovDocumentList>();
		fetched = gson.fromJson(json, GovSearchResult.class).dokumentlista;
		
		
		result.add(fetched);
		
		if(fetched.warning != null && fetched.traffar > TRAFFAR_LIMIT)
		{
			System.out.println(fetched.warning);
			
			//Create two new requests with the same period divided into two
			GovDate mean = request.period.returnMean();
			System.out.println("From: " + request.period.getFrom());
			System.out.println("To: " + request.period.getTo());
			System.out.println("Mean : " + mean);
			Period period1 = new Period(request.period.from, mean);
			GovFetchRequest req1 = new GovFetchRequest(request.searchString, request.rm , period1 , request.ts, request.bet,
					request.tempbet, request.nr, request.organ, request.commissionerId, request.parties);
			
			Period period2 = new Period(new GovDate(mean.year, mean.month, mean.day + 1), request.period.to);
			GovFetchRequest req2 = new GovFetchRequest(request.searchString, request.rm , period2 , request.ts, request.bet,
					request.tempbet, request.nr, request.organ, request.commissionerId, request.parties);
			
			result.addAll(fetchDocs(req1));
			result.addAll(fetchDocs(req2));
		}
		else {
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
		}
		
		return result;
		
	}
	
	public ArrayList<GovDocumentList> fetchAllDocs() throws Exception
	{
		Calendar d = Calendar.getInstance();
		return fetchDocs(new GovFetchRequest("", "", new Period(new GovDate(1900, 1, 1),new GovDate(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH))), "", "", "", "", "", new ArrayList<String>()));
	}
}
