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

  public static void initBuilder() {
    wcc = new WordCountCategoriser();

    initialised = true;
  }

  public static boolean isInitialised() {
    return initialised;
  }

  public static ESDocument createESDocument(GovDocumentLite doc) {

    if (!isInitialised()) {
      System.out.println("Error: ESDocumentbuilder is not initialised, cant build documents.");
      return null;
    }

    String docId = doc.getId();

    DateTime dt = new DateTime(doc.getDatum());
    long publishedTimestamp = dt.getMillis() / 1000;
    long fetchedTimestamp = new DateTime().getMillis() / 1000;

    String title = doc.getTitel();

    // Party
    String[] party;
    if (doc.getUndertitel() == null) {
      throw new NullPointerException("Motion " + doc.getId()
          + ": undertitle is null, can't specify party");
    } else {
      party = createParty(doc.getUndertitel());
    }

    String text = doc.getText();

    ESDocument eSDoc =
        new ESDocument(docId, publishedTimestamp, fetchedTimestamp, title, null, party, text);

    eSDoc.category = wcc.categorise(eSDoc);

    return eSDoc;
  }

  public static String[] createParty(String underTitle) {

    String[] splittedUnderTitle = underTitle.split(" \\(");

    if (splittedUnderTitle.length != 2) {
      throw new IllegalArgumentException("Motions undertitle contains more than one paranthese.");
    }

    String partiesUnformatted =
        splittedUnderTitle[1].substring(0, (splittedUnderTitle[1].length() - 1));
    String[] parties = partiesUnformatted.split(", ");
    return parties;
  }
}
