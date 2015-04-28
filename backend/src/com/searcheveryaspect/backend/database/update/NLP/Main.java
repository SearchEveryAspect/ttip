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
    ESDocument es = new ESDocument("32343", 3232323, 3, "Swag och andra betingelser", null, "S", "Vänsterpartiet välkomnar"
    		+ " i stort regeringens proposition och de förslag som läggs fram. Vi delar regeringens uppfattning att det krävs"
    		+ " en ambitionshöjning för den förnybara elproduktionen. Klimatfrågan är vår tids ödesfråga och det är viktigt att Sverige "
    		+ "ligger i framkant vad gäller förnybar elproduktion. Regeringens förslag att Sverige, inom ramen för elcertifikatssystemet, ska finansiera 3"
    		+ "0 terawattimmar ny förnybar elproduktion till 2020 jämfört med 2002, är därför ett viktigt steg på vägen.");
    
    try {
    	NLP.init();
		String[] c = NLP.categorize(es);
		
		for(String s : c)
		{
			System.out.println("category: " +  s.toString());
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}

