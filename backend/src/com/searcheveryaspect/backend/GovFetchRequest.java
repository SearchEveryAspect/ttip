package com.searcheveryaspect.backend;

import java.util.ArrayList;


public class GovFetchRequest 
{
	
	private final String RIKSDAG_URL = "http://data.riksdagen.se/dokumentlista/";
	//Sökord
	String searchString;
	
	String rm;
	Period period;
	String ts;
	String bet;
	String tempbet;
	String nr;
	String organ;
	int commissionerId; //-1 if not used
	ArrayList<String> parties;
	
	public GovFetchRequest(String searchString, String rm, Period period, String ts, String bet,
			String tempbet, String nr, String organ, int commissionerId, ArrayList<String> parties)
	{
		this.searchString = searchString;
		this.rm = rm;
		this.period = period;
		this.ts = ts;
		this.bet = bet;
		this.tempbet = tempbet;
		this.nr = nr;
		this.organ = organ;
		this.commissionerId = commissionerId;
		this.parties = parties;
	}
	
	public GovFetchRequest(String searchString, String rm, Period period, String ts, String bet,
			String tempbet, String nr, String organ, ArrayList<String> parties)
	{
		this.searchString = searchString;
		this.rm = rm;
		this.period = period;
		this.ts = ts;
		this.bet = bet;
		this.tempbet = tempbet;
		this.nr = nr;
		this.organ = organ;
		this.commissionerId = -1;
		this.parties = parties;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(RIKSDAG_URL);
		sb.append("?sok=");
		sb.append(searchString);
		sb.append("&doktyp=mot&rm=");
		sb.append(rm);
		sb.append("&from=");
		sb.append("2010-01-01");
		sb.append("&tom=");
		sb.append("2015-09-09");
		sb.append("&ts=");
		sb.append(ts);
		sb.append("&bet=");
		sb.append(bet);
		sb.append("&tempbet=");
		sb.append(tempbet);
		sb.append("&nr=");
		sb.append("");
		sb.append("&org=");
		sb.append(organ);
		sb.append("&iid=");
		if(commissionerId != -1)
			sb.append(commissionerId);
		for(int i = 0; i < parties.size(); i++)
		{
			sb.append("&parti=");
			sb.append(parties.get(i));
		}
		sb.append("&webbtv=&talare=&exakt=&planering=&sort=rel&sortorder=desc&rapport=&utformat=json&a=s#soktraff");		
		return sb.toString();
	}
}
