package com.searcheveryaspect.backend;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Main {
	
	static final ArrayList<String> parties = new ArrayList<String>(Arrays.asList(new String[]{"S","M","FP","KD", "V", "SD", "C", "MP", "NYD", "-"}));
	 
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		ArrayList<String> selectedParties = new ArrayList<String>();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Enter search string: ");
		String search = in.readLine();
		System.out.print("\nEnter start year: ");
		String startYear = in.readLine();
		System.out.print("\nEnter end year: ");
		String endYear = in.readLine();
		
		while(true)
		{
			System.out.print("Enter a party from list: ");
			for(String s : parties)
			{
				System.out.print(s + " ");
			}
			System.out.println("\n Enter exit to finish: ");
			String party = in.readLine();
			if(party.equals("exit"))
				break;
			if(parties.contains(party))
				selectedParties.add(party);
			else
				System.out.println("Party is not in list.");
		}
		
		GovClient gv = new GovClient();
		int startYearInt = 2010;
		int endYearInt = 2015;
		try
		{
			startYearInt = Integer.parseInt(startYear);
			endYearInt = Integer.parseInt(endYear);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Can't parse number input; using default values: " + startYearInt + " to " + endYearInt);
		}
		
		ArrayList<GovDocumentList> gdl = gv.fetchDocs(new GovFetchRequest(search, "", new Period(new GovDate(startYearInt, 1, 1), new GovDate(endYearInt, 12, 31)), "", "", "", "", "", selectedParties));
		
		for(GovDocumentList g : gdl)
		{
			System.out.println(g);
		}
		
		System.out.print(gdl);
	}

}
