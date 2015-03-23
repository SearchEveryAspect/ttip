/**
 * 
 */
package com.searcheveryaspect.backend;

public class GovDocumentLite
{
	int traff;
	String datum;
	String id;
	String dokument_url_text;
	String titel;
	String undertitel;
	String organ;
	String doktyp;
	GovAppendix filbilaga;
	
	public GovDocumentLite(int traff, String datum, String id, String dokument_url_text, String titel, String undertitel, String organ, String doktyp, GovAppendix filbilaga)
	{
		this.traff = traff;
		this.datum = datum;
		this.id = id;
		this.dokument_url_text = dokument_url_text;
		this.titel = titel;
	    this.undertitel = undertitel;
		this.organ = organ;
		this.doktyp = doktyp;
		this.filbilaga = filbilaga;
	}
	
	public String toString()
	{
		return "Träff: " + traff + " Datum: " + datum + " Id: " + id + " Dokument_url_text: " + dokument_url_text + " Titel: " + titel + " Undertitel: " + undertitel +  " Organ: " + organ + " Doktyp: " + doktyp;
	}
}
