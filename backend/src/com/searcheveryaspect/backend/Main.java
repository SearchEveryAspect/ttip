package com.searcheveryaspect.backend;

import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		GovClient gv = new GovClient();
		ArrayList<String> list  = new ArrayList<>();
		list.add("S");
		list.add("MP");
		list.add("C");
		list.add("V");
		
		GovDocumentList gdl = gv.fetchDocs(new GovFetchRequest("Skatt", "", new Period(new GovDate(2010, 1, 1), new GovDate(2015, 11, 1)), "", "", "", "", "", list));
		
		System.out.print(gdl);
	}

}
