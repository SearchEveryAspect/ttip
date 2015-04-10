/**
 * 
 */
package com.searcheveryaspect.backend;

import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.ArrayList;
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
	}
	
	public static void testParties()
	{
		
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
		assert t.toString() == "GovDocumentList [Datum: 2015-04-10 12:37:27 Sida: 1 Sidor: 0 Traff Från: 1 Träffar: 0]";
	}
	
}
