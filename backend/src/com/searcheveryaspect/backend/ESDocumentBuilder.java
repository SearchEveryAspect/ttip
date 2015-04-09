package com.searcheveryaspect.backend;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * Create an ESDocument	
 * @author Jacqueline Eriksson
 *
 */

public class ESDocumentBuilder {
	
	
	// tar ett GovDocumentLite 
	public ESDocument createESDocument(GovDocumentLite doc){
				
		//TODO check that date in the GovDocumentLite object has "yyyy-MM-dd" format 
		String docId = doc.getId();
		
		String publishedTimestamp = doc.getDate(); //fast vill ju inte ha datumet i stäng format 
			
		LocalDate localDate = new LocalDate();		 
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		String fetchedTimestamp = formatter.print(localDate);
		
		String title = doc.getTitle();
		
		
		//TODO implement NLP to get category 
		
		String[] category = new String[]{"Unknown"}; //uttnyttja sedan urltext grejen 
		
		//party ->hur får jag ut partiet? tittar jag på vilka personer som skrivit motionen för att därefter gå in och titta på 
		//vilket parti dessa personer är ifrån. 
		//http://data.riksdagen.se/dokumentlista/?sok=&doktyp=mot&rm=&from=&tom=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=rel&sortorder=desc&rapport=&utformat=json&a=s#soktraff
		//undertitel innehåller infon behöver i så fall parsa  fram det som står inom parantes(det sista "ordet")
		
		
		String underTitle = doc.getUnderTitle();
		String[] splittedUnderTitle = underTitle.split(" ");
		String partyInBrackets = splittedUnderTitle[splittedUnderTitle.length-1]; 
		String party = partyInBrackets.substring(1, (partyInBrackets.length()-1));
		
		ESDocument eSDoc = new ESDocument(docId, publishedTimestamp, fetchedTimestamp, title, category, party);
		
		return eSDoc;
		
	}

}
	

