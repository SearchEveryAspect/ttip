/**
 * 
 */
package com.searcheveryaspect.backend;

import com.google.gson.annotations.SerializedName;

/**
 * @author lundblom
 *
 */
public class GovDocumentList 
{
	@SerializedName("@datum")
	private String datum = "";
	@SerializedName("@sida")
	private int sida = 0;
	@SerializedName("@sidor")
	private int sidor = 0;
	@SerializedName("@traff_fran")
	private int traff_fran = 0;
	@SerializedName("@traffar")
	private int traffar = 0;
	private GovDocument[] dokument = new GovDocument[0];
	
	public GovDocumentList(String datum, int sida, int sidor, int traff_fran, int traffar, GovDocument[] dokument)
	{
		this.datum = datum;
		this.sida = sida;
		this.sidor = sidor;
		this.traff_fran = traff_fran;
		this.traffar = traffar;
		this.dokument = dokument;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("GovDocumentList [");
		sb.append("Datum: " + datum + " Sida: " + sida + " Sidor: " + sidor + " Traff Från: " + traff_fran + " Träffar: " + traffar + " Antal dokument: " + dokument.length);
		sb.append("\n Dokument:");
		
		for(int i = 0; i < dokument.length; i++)
		{
			sb.append(" Dokument nr ");
			sb.append(i);
			sb.append(": ");
			sb.append(dokument[i].toString());
			sb.append("\n");
		}
		
		sb.append("]");
		return sb.toString();
	}
}
