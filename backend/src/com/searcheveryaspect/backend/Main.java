package com.searcheveryaspect.backend;

import java.beans.FeatureDescriptor;
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
		
		ArrayList<GovDocumentList> gdl = gv.fetchDocs(new GovFetchRequest("Skatt", "", new Period(new GovDate(2010, 1, 1), new GovDate(2015, 11, 1)), "", "", "", "", "", list));
		
		for(GovDocumentList g : gdl)
		{
			System.out.println(g);
		}
		
		System.out.print(gdl);
	}

}
