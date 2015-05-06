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


	public static String[] createParty(String underTitle) {
	
		String[] splittedUnderTitle = underTitle.split(" \\(");

		if(splittedUnderTitle.length != 2) {
			throw new IllegalArgumentException("Motions undertitle contains more than one paranthese.");
		}
		
		String partiesUnformatted = splittedUnderTitle[1].substring(0, (splittedUnderTitle[1].length()-1));
		String[] parties = partiesUnformatted.split(", ");
		return parties;
	}
	
  public static ESDocument createESDocument(GovDocumentLite doc) {

    String docId = doc.getId();

    DateTime dt = new DateTime(doc.getDatum());
    long publishedTimestamp = dt.getMillis() / 1000;

    long fetchedTimestamp = new DateTime().getMillis() / 1000;

    String title = doc.getTitel();
    
     // TODO implement NLP to get category
    String[] category = new String[] {"Unknown"}; // uttnyttja sedan urltext

    String[] party;
    // party
    if(doc.getUndertitel() == null){
    	throw new NullPointerException("undertitle is null, can't specify party");
    } else {
    	party = createParty(doc.getUndertitel());
    }
  

   ESDocument eSDoc =
        new ESDocument(docId, publishedTimestamp, fetchedTimestamp, title, category, party);

    return eSDoc;

  }

}
