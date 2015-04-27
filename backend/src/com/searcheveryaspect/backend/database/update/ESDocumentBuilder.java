package com.searcheveryaspect.backend.database.update;


import org.joda.time.DateTime;
/**
 * 
 * Create an ESDocument	
 * @author Jacqueline Eriksson
 *
 */

public class ESDocumentBuilder {
	
	
public ESDocument createESDocument(GovDocumentLite doc){
				
		String docId = doc.getId();
		
		DateTime dt = new DateTime(doc.getDate());
		long publishedTimestamp = dt.getMillis() /1000;
		
		long fetchedTimestamp = new DateTime().getMillis() / 1000;
		
		String title = doc.getTitle();		
		
		//TODO implement NLP to get category 		
		String[] category = new String[]{"Unknown"}; //uttnyttja sedan urltext 
		
		//party 		
		String underTitle = doc.getUnderTitle();
		String[] splittedUnderTitle = underTitle.split(" ");
		String partyInBrackets = splittedUnderTitle[splittedUnderTitle.length-1]; 
		String party = partyInBrackets.substring(1, (partyInBrackets.length()-1));
		
		ESDocument eSDoc = new ESDocument(docId, publishedTimestamp, fetchedTimestamp, title, category, party);
		
		return eSDoc;
		
	}

}
	

