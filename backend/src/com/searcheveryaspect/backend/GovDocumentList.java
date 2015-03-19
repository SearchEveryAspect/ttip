/**
 * 
 */
package com.searcheveryaspect.backend;

/**
 * @author lundblom
 *
 */
public class GovDocumentList 
{
	private String datum = "";
	private int sida = 0;
	private int sidor = 0;
	private int traff_fran = 0;
	private int traffar = 0;
	private GovDocument[] dokument = new GovDocument[0];
	
	public String toString()
	{
		return "Datum: " + datum + " Sida: " + sida + " Sidor: " + sidor + " Traff Från: " + traff_fran + " Träffar: " + "traffar" + " Antal dokument: " + dokument.length;
	}
}
