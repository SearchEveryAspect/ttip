package com.searcheveryaspect.backend.database.update;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * 
 */
public class ESDocumentBuilderTest {

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
  
  /**
   * Test createESDocument. initialise govdocumentlite and expected esdocument and
   * call assertequals
   */
  @Test
  public void correctGovDocLiteTest() {
	  // TODO
  }
  
  /**
   * Initialise wrong govdocumentlite and expected esdocument (meybeh null?), assertequals
   */
  @Test
  public void wrongGovDocLiteIdTest() {
	  // TODO
  }
  
  @Test
  public void wrongGovDocLiteDatumTest() {
	  // TODO
  }
  
  @Test
  public void wrongGovDocLiteTitelTest() {
	  // TODO
  }
  
  @Test
  public void wrongGovDocLiteUndertitelTest() {
	  // TODO
  }
  
}
