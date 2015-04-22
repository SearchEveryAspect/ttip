package com.searcheveryaspect.backend;

/**
 * 
 * Specifies the ESDocument object
 * @author Jacqueline Eriksson
 *
 */

public class ESDocument {
	String docId;
	String publishedTimestamp;
	String fetchedTimestamp;
	String title;
	String[] category;
	String party;
	
	public ESDocument(String docId, String publishedTimestamp, String fetchedTimestamp, String title, String[] category, String party) {
	this.docId = docId;
	this.publishedTimestamp = publishedTimestamp;
	this.fetchedTimestamp = fetchedTimestamp;
	this.title = title;
	this.category = category;
	this.party = party;		
	}

  /**
   * @return
   */
  public String getDocId() {
    return null;
  }
	
	
	

}
