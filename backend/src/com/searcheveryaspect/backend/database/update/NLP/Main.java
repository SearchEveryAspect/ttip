package com.searcheveryaspect.backend.database.update.NLP;


import com.beust.jcommander.JCommander;
import com.searcheveryaspect.backend.database.update.ESDocument;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

public class Main {

  public static void main(String[] args) 
  {
    ESDocument es = new ESDocument("32343", 3232323, 3, "Swag och andra betingelser", null, "S", "Riksdagen tillkännager för "
    		+ "regeringen som sin mening vad som anförs i motionen om att regeringen bör återkomma till riksdagen med "
    		+ "förslag på hur torvanvändningen ska fasas ut ur elcertifikatssystemet, med inriktningen att nya torvtäkter inte "
    		+ "ska öppnas.");
    
    try {
    	NLP.init();
		NLP.categorize(es);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}

