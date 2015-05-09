package com.searcheveryaspect.backend.database.update;


import org.joda.time.DateTime;

/**
 * 
 * Create an ESDocument
 * 
 * @author Jacqueline Eriksson
 * 
 */

public class ESDocumentBuilder {
	
	static WordCountCategoriser wcc;
	private static boolean initialised = false;
	
	public static void initBuilder()
	{
		wcc = new WordCountCategoriser();
		
		initialised = true;
	}

	public static boolean isInitialised()
	{
		return initialised;
	}

	public static ESDocument createESDocument(GovDocumentLite doc) {
		
		if(!isInitialised())
		{
			System.out.println("Error: ESDocumentbuilder is not initialised, cant build documents.");
			return null;
		}

	    String docId = doc.getId();
	
	    DateTime dt = new DateTime(doc.getDatum());
	    long publishedTimestamp = dt.getMillis() / 1000;
	
	    long fetchedTimestamp = new DateTime().getMillis() / 1000;
	
	    String title = doc.getTitel();
	
	
	    // party
	    String underTitle = doc.getUndertitel();
	    String[] splittedUnderTitle = underTitle.split(" ");
	    String partyInBrackets = splittedUnderTitle[splittedUnderTitle.length - 1];
	    String party = partyInBrackets.substring(1, (partyInBrackets.length() - 1));
	    String text = doc.getText();
	
	    ESDocument eSDoc =
	        new ESDocument(docId, publishedTimestamp, fetchedTimestamp, title, null, party, text);
	    
	    eSDoc.category = wcc.categorise(eSDoc);
	
	    return eSDoc;

	}

}
