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
				
		//måste se till att date i doc id har rätt format	
		String docId = doc.getId();
		
		String publishedTimestamp = doc.getDate(); //fast vill ju inte ha datumet i stäng format 
			
		LocalDate localDate = new LocalDate();		 
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		String fetchedTimestamp = formatter.print(localDate);
		
		String title = doc.getTitle();
		
		String[] category = new String[]{"Unknown"};
		
		//categhory-> hämtas ur NLP // Vill man hitta caqtegorier innaan man skapar JSON, skapas JSON doc i ES documentbuilder för att 
		//sedan skickas in i databsen i ElasticSearchPut? vad används i så fall ESDocument till? Sätt till unknown så länge 
		
		//party ->hur får jag ut partiet? tittar jag på vilka personer som skrivit motionen för att därefter gå in och titta på 
		//vilket parti dessa personer är ifrån. 
		
		String party = "Unknown";
		
		ESDocument eSDoc = new ESDocument(docId, publishedTimestamp, fetchedTimestamp, title, category, party);
		
		return eSDoc;
		
	}

}
	

