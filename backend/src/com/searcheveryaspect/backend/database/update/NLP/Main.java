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
    ESDocument es = new ESDocument("32343", 3232323, 3, "Swag och andra betingelser", null, "S", "Vi föreslår därför en ändring av riksdagsordningen med fokus på att utvidga möjligheterna för att enskilda riksdagsledamöter ska kunna lämna in motioner under hela riksmötet, och att riksdagen bifaller denna motion samt därmed ger riksdagsstyrelsen innehållet och motiven för förslaget i denna tillkänna.");
    
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

