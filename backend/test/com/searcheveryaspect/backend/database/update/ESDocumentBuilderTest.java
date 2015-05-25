package com.searcheveryaspect.backend.database.update;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.searcheveryaspect.backend.shared.Category;

/**
 * 
 */
public class ESDocumentBuilderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createPartyCommaTest() {
		final String input = "av Jane Doe m.fl. (M, C, FP, KD)";
		final String[] expected = new String[] {"M", "C", "FP", "KD"};
		assertArrayEquals(expected, ESDocumentBuilder.createParty(input));
	}

	@Test
	public void createPartyMultipleParenthesisTest() {
		final String input = "av Jane Doe (V) och John Doe (S)";
		final String[] expected = new String[] {"V", "S"};
		assertArrayEquals(expected, ESDocumentBuilder.createParty(input));
	}

	/*
	 * Test createESDocument. initialise govdocumentlite and expected esdocument and
	 * call assertequals
	 * 
	 * FOR CORRECT DOCUMENT
	 */

	private GovDocumentLite correctGovDocLite() {
		int traff = 1;
		String datum = "2014-06-10";
		String id = "abc";
		String dokument_url_text = "http://www.ekjjdfphkg.com";
		String text = "Bla bla skatt bla";
		String titel = "titelstuff";
		String undertitel = "av Jane Doe m.fl. (M, C, FP, KD)";
		String organ = "organstuff";
		String doktyp = "JSON";
		GovAppendix filbilaga = null;
		GovDocumentLite liteDoc = new GovDocumentLite(traff, datum, id, dokument_url_text, text, titel, 
				undertitel, organ, doktyp, filbilaga);
		return liteDoc;
	}

	@Test
	public void correctGovDocLiteNotPartyCategoryTest() {
		// Create govdoclite
		GovDocumentLite liteDoc = correctGovDocLite();
		DateTime dt = new DateTime(liteDoc.getDatum());
		long fetched = (new DateTime()).getMillis() / 1000;
		long published = dt.getMillis() / 1000;

		// Create esdocument with the builder
		ESDocumentBuilder.initBuilder();
		ESDocument res = ESDocumentBuilder.createESDocument(liteDoc);

		// Exclude testing of the arrays since they need assertarrayequals
		String[] category = res.getCategory();
		String[] party = res.getParty();

		ESDocument expected = new ESDocument(liteDoc.getId(), published, fetched, liteDoc.getTitel(), 
				category, party, liteDoc.getText());

		assertEquals(expected, res);
	}

	/*
	 * AssertArrayEquals tests for the same correct govdoclite
	 */

	@Test
	public void correctGovDocLitePartyTest() {
		// Create govdoclite
		GovDocumentLite liteDoc = correctGovDocLite();

		// Create esdocument with the builder
		ESDocumentBuilder.initBuilder();
		ESDocument res = ESDocumentBuilder.createESDocument(liteDoc);

		// Create expected esdocument
		String[] category = {"skatt"};
		String[] party = {"M", "C", "FP", "KD"};
		DateTime dt = new DateTime(liteDoc.getDatum());
		long published = dt.getMillis() / 1000;
		long fetched = (new DateTime()).getMillis() / 1000;

		ESDocument expected = new ESDocument(liteDoc.getId(), published, fetched, liteDoc.getTitel(), 
				category, party, liteDoc.getText());

		assertArrayEquals(expected.getParty(), res.getParty());
	}

	@Test
	public void correctGovDocLiteCategoryTest() {
		// Create govdoclite
		GovDocumentLite liteDoc = correctGovDocLite();

		// Create esdocument with the builder
		ESDocumentBuilder.initBuilder();
		ESDocument res = ESDocumentBuilder.createESDocument(liteDoc);

		// Create expected esdocument
		String[] category = {"skatt"};
		String[] party = {"M", "C", "FP", "KD"};
		DateTime dt = new DateTime(liteDoc.getDatum());
		long published = dt.getMillis() / 1000;
		long fetched = (new DateTime()).getMillis() / 1000;

		ESDocument expected = new ESDocument(liteDoc.getId(), published, fetched, liteDoc.getTitel(), 
				category, party, liteDoc.getText());

		assertArrayEquals(expected.getCategory(), res.getCategory());
	}

	/*
	 * Test that exceptions are thrown when the builder is called on govdoclite with incorrect values
	 * 
	 * FOR INCORRECT DOCUMENT
	 */

	@Test
	public void wrongGovDocLiteIdTest() {
		// Create govdoclite
		int traff = 1;
		String datum = "2014-05-10";
		String id = null;
		String dokument_url_text = "http://www.ekjjdfphkg.com";
		String text = "Bla bla skatt bla";
		String titel = "titelstuff";
		String undertitel = "av Jane Doe m.fl. (M, C, FP, KD)";
		String organ = "organstuff";
		String doktyp = "JSON";
		GovAppendix filbilaga = null;
		GovDocumentLite liteDoc = new GovDocumentLite(traff, datum, id, dokument_url_text, text, titel, 
				undertitel, organ, doktyp, filbilaga);

		// Setup exception
		thrown.expect(NullPointerException.class);

		// Create esdoc
		ESDocumentBuilder.initBuilder();
		ESDocumentBuilder.createESDocument(liteDoc);
	}

	@Test
	public void wrongGovDocLiteDatumTest() {
		// Create govdoclite
		int traff = 1;
		String datum = "OupsWrongFormat";
		String id = "abc";
		String dokument_url_text = "http://www.ekjjdfphkg.com";
		String text = "Bla bla skatt bla";
		String titel = "titelstuff";
		String undertitel = "av Jane Doe m.fl. (M, C, FP, KD)";
		String organ = "organstuff";
		String doktyp = "JSON";
		GovAppendix filbilaga = null;
		GovDocumentLite liteDoc = new GovDocumentLite(traff, datum, id, dokument_url_text, text, titel, 
				undertitel, organ, doktyp, filbilaga);

		// Setup exception
		thrown.expect(IllegalArgumentException.class);

		// Create esdoc
		ESDocumentBuilder.initBuilder();
		ESDocumentBuilder.createESDocument(liteDoc);
	}

	@Test
	public void wrongGovDocLiteTitelTest() {
		// Create govdoclite
		int traff = 1;
		String datum = "2014-05-10";
		String id = "abc";
		String dokument_url_text = "http://www.ekjjdfphkg.com";
		String text = "Bla bla skatt bla";
		String titel = null;
		String undertitel = "av Jane Doe m.fl. (M, C, FP, KD)";
		String organ = "organstuff";
		String doktyp = "JSON";
		GovAppendix filbilaga = null;
		GovDocumentLite liteDoc = new GovDocumentLite(traff, datum, id, dokument_url_text, text, titel, 
				undertitel, organ, doktyp, filbilaga);

		// Setup exception
		thrown.expect(NullPointerException.class);

		// Create esdoc
		ESDocumentBuilder.initBuilder();
		ESDocumentBuilder.createESDocument(liteDoc);
	}

	@Test
	public void wrongGovDocLiteUndertitelTest() {
		// Create govdoclite
		int traff = 1;
		String datum = "2014-07-10";
		String id = "abc";
		String dokument_url_text = "http://www.ekjjdfphkg.com";
		String text = "Bla bla skatt bla";
		String titel = "titelstuff";
		String undertitel = null;
		String organ = "organstuff";
		String doktyp = "JSON";
		GovAppendix filbilaga = null;
		GovDocumentLite liteDoc = new GovDocumentLite(traff, datum, id, dokument_url_text, text, titel, 
				undertitel, organ, doktyp, filbilaga);

		// Setup exception
		thrown.expect(NullPointerException.class);

		// Create esdoc
		ESDocumentBuilder.initBuilder();
		ESDocumentBuilder.createESDocument(liteDoc);
	}

}
