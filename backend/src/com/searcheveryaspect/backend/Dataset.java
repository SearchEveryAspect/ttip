/**
 * 
 */
package com.searcheveryaspect.backend;

/**
 * Dataset of a motion. Contains the party that published it and a list containing ints,
 * could it be dates? TODO
 * @author Mitra
 *
 */
public class Dataset {
	String party;
	int[] data;
	
	public Dataset(String party, int[] data) {
		this.party = party;
		this.data = data;
	}
	
	public String getParty(){
		return party;
	}
	
	public int[] getData() {
		return data;
	}

}
