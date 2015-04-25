package com.searcheveryaspect.backend;

/**
 * 
 * Specifies the ESDocument object
 * 
 * @author Jacqueline Eriksson
 * 
 */

public class ESDocument {
  String docId;
  long publishedTimestamp; // in unix
  long fetchedTimestamp; // in unix
  String title;
  String[] category;
  String party;


  public ESDocument(String docId, long publishedTimestamp, long fetchedTimestamp, String title,
      String[] category, String party) {
    this.docId = docId;
    this.publishedTimestamp = publishedTimestamp;
    this.fetchedTimestamp = fetchedTimestamp;
    this.title = title;
    this.category = category;
    this.party = party;
  }


  public String getDocId() {
    return docId;
  }


}
