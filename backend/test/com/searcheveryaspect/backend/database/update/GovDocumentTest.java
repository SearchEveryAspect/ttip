/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Mitra
 *
 */
public class GovDocumentTest {
	
	@Test
	public void checkGovDoc() {
		int traff = 1; String datum = "2014-01-01"; String id = "abc"; String dokument_url_text = "http://www.lol.se";
		String titel = "Testar"; String undertitel = "Med junit"; String organ = "Lever"; String doktyp = "JSON";
		GovAppendix filbilaga = null;
		GovDocument govDoc = new GovDocument(traff, datum, id, dokument_url_text, titel, undertitel, organ, doktyp, filbilaga);
		
		String expected = "Träff: 1 Datum: 2014-01-01 Id: abc Dokument_url_text: http://www.lol.se Titel: Testar Undertitel: Med junit Organ: Lever Subtyp: null Doktyp: JSON";
		
		assertEquals(expected, govDoc.toString());
	}

}
