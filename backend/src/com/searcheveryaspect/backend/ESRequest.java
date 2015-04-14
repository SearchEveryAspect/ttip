/**
 * 
 */
package com.searcheveryaspect.backend;

/**
 * Contains information about what the ESQuerier will search for.
 * Get search info from frontend.
 * Creates a querier depending on information in fields and querier returns the result.
 * Then creates a Response depending on the search result.
 * @author Mitra
 *
 */
public class ESRequest {
	String[] party;
	Period period;
	String[] category;
	
	public ESRequest() {
		
	}

}
