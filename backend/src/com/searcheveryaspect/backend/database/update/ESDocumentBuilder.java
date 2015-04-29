package com.searcheveryaspect.backend.database.update;


import com.searcheveryaspect.backend.shared.Category;

import org.joda.time.DateTime;

import java.util.Random;

/**
 * 
 * Create an ESDocument
 * 
 * @author Jacqueline Eriksson
 * 
 */

public class ESDocumentBuilder {


  public static ESDocument createESDocument(GovDocumentLite doc) {

    String docId = doc.getId();

    DateTime dt = new DateTime(doc.getDatum());
    long publishedTimestamp = dt.getMillis() / 1000;

    long fetchedTimestamp = new DateTime().getMillis() / 1000;

    String title = doc.getTitel();

    // TODO implement NLP to get category
    Random rand = new Random();
    String[] category =
        new String[] {Category.values()[rand.nextInt(Category.values().length)].toString()}; // uttnyttja
                                                                                             // sedan
                                                                                             // urltext

    // party
    String party;
    String underTitle = doc.getUndertitel();
    if (underTitle != null) {
      String[] splittedUnderTitle = underTitle.split(" ");
      String partyInBrackets = splittedUnderTitle[splittedUnderTitle.length - 1];
      party = partyInBrackets.substring(1, (partyInBrackets.length() - 1));
    } else {
      party = "UNKNOWN";
    }

    ESDocument eSDoc =
        new ESDocument(docId, publishedTimestamp, fetchedTimestamp, title, category, party);

    return eSDoc;

  }

}
