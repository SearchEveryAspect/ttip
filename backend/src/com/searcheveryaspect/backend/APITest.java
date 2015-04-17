/**
 * 
 */
package com.searcheveryaspect.backend;

import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.*;

/**
 * @author dajmmannen
 *
 */
public final class APITest 
{

	private static ArrayList<GovDocumentList> QueryAPI(String search, int startYear, int endYear, ArrayList<String> parties) throws Exception
	{
		GovClient gv = new GovClient();
		
		GovFetchRequest request = new GovFetchRequest(search, "", new Period(new GovDate(startYear, 1, 1), new GovDate(endYear, 12, 31)), "", "", "", "", "", parties);
		return gv.fetchDocs(request);
	}
	
	public static void RunAllTests()
	{
		testEmptyResult();
		testOneResult();
		testManyResults();
		testParties();
		testYears();
	}
	
	public static void testEmptyResult()
	{
		ArrayList<GovDocumentList> t = null;
		try {
			t = QueryAPI("thisshouldreturnnothing", 1900, 2015, new ArrayList<String>());
		} catch (Exception e) {
		}
		assert t != null;
		String s = t.toString();
		
		String dataS = s.substring(45, s.length() - 1);
		assert dataS.equals("Sida: 1 Sidor: 0 Traff Från: 1 Träffar: 0]");
		

		try{
			t = QueryAPI("", 1000, 1800, new ArrayList<String>());
		} catch (Exception e) {
		}
		assert t != null;
		s = t.toString();
		
		dataS = s.substring(45, s.length() - 1);
		assert dataS.equals("Sida: 1 Sidor: 0 Traff Från: 1 Träffar: 0]");
		
		try{
			t = QueryAPI("", 2100, 2200, new ArrayList<String>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		assert t != null;
		s = t.toString();
		
		dataS = s.substring(45, s.length() - 1);
		assert dataS.equals("Sida: 1 Sidor: 0 Traff Från: 1 Träffar: 0]");
		
	}
	
	//WILL RETURN AN ERROR CURRENTLY
	public static void testOneResult()
	{
		ArrayList<GovDocumentList> t = null;
		try {
			t = QueryAPI("Svanskupering på hundar", 1990, 1990, new ArrayList<String>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		assert t != null;
		assert t.toString() == "GovDocumentList [Datum: 2015-04-10 12:37:27 Sida: 1 Sidor: 0 Traff Från: 1 Träffar: 0]";
		
		try {
			t = QueryAPI("kaffe te matte", 1975, 1975, new ArrayList<String>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		assert t != null;
		
		t.get(0).dokument[0].titel.equals("om slopande av mervärdeskatten på livsmedel, m. m.");
		t.get(0).dokument[0].datum.equals("1975-01-25");
		
	}
	
	public static void testYears()
	{
		ArrayList<GovDocumentList> t1 = null;
		ArrayList<GovDocumentList> t2 = null;
		try {
			t1 = QueryAPI("skatt", 2010, 2010, new ArrayList<String>());
			t2 = QueryAPI("skatt", 2011, 2011, new ArrayList<String>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		for(int i = 0; i < t1.size(); i++)
		{
			for(int j = 0; j < t2.size(); j++)
			{
				assert !t1.get(i).equals(t2.get(j));
			}
		}	
	}
	
	public static void testParties()
	{
		ArrayList<GovDocumentList> t1 = null;
		ArrayList<GovDocumentList> t2 = null;
		try {
			t1 = QueryAPI("", 2000, 2000, new ArrayList<String>());
			t2 = QueryAPI("", 2000, 2000, new ArrayList<String>(Arrays.asList(new String[]{"S"})));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		assert t1 != null;
		assert t2 != null;
		assert t1.size() != t2.size();
		
		try {
			t1 = QueryAPI("", 2000, 2000, new ArrayList<String>(Arrays.asList(new String[]{"S"})));
			t2 = QueryAPI("", 2000, 2000, new ArrayList<String>(Arrays.asList(new String[]{"KD"})));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		assert t1 != null;
		assert t2 != null;
		assert t1.size() != t2.size();
	}
	
	public static void testManyResults()
	{
		ArrayList<GovDocumentList> t = null;
		java.util.Timer timer = new java.util.Timer();
		java.util.TimerTask task = new java.util.TimerTask() {
			
			@Override
			public void run() {
				System.out.println("Error: Search too slow");
				System.exit(0);
				
			}
		};
		
		timer.schedule(task, 240000);
		try {
			t = QueryAPI("", 2000, 2002, new ArrayList<String>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		timer.cancel();
		assert t != null;

		
		timer.schedule(task, 480000);
		try {
			t = QueryAPI("", 2002, 2005, new ArrayList<String>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		timer.cancel();
		assert t != null;
	}
	
}
