/**
*
 *  //http://data.riksdagen.se/dokumentlista/?sok=&doktyp=mot&rm=&from=&tom=&ts=&bet=&tempbet=&nr=&org=&iid=&webbtv=&talare=&exakt=&planering=&sort=rel&sortorder=desc&rapport=&utformat=json&a=s#soktraff
 */
package com.searcheveryaspect.backend;

public class GovDocumentLite
{
	int traff; 
	String datum; //datum som motionen pulicerades
	String id;
	String dokument_url_text; //länk till motionen i textformat 
	String titel; 
	String undertitel; //vilka som gjort motionen och vilket parti de är från inom parantes ex "av Kenneth Johansson (c)"
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
	
	public String getId() {
		return id; 
	}
	
	public String getTitle() {
		return titel;
	}
	
	public String getDate() {
		return datum;
	}

	public String getUnderTitle() {
		return undertitel;
	}
	
	public String toString()
	{
		return "TrÃ¤ff: " + traff + " Datum: " + datum + " Id: " + id + " Dokument_url_text: " + dokument_url_text + " Titel: " + titel + " Undertitel: " + undertitel +  " Organ: " + organ + " Doktyp: " + doktyp;
	}
}
