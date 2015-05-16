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
}
