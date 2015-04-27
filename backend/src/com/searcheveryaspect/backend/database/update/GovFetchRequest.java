package com.searcheveryaspect.backend.database.update;


import java.util.ArrayList;


public class GovFetchRequest
{

	private final String RIKSDAG_URL = "http://data.riksdagen.se/dokumentlista/";
	//Sökord
	String searchString;
	//Riksmöte
	String rm;

	Period period;
	//Systemdatum
	String ts;
	//Beteckning
	String bet;
	//Tempbet
	String tempbet;
	//Nummer
	String nr;
	//Utskott/Organ
	String organ;
	//Ledamot
	int commissionerId; //-1 if not used
	//List of different parties
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
		sb.append(searchString.replace(" ", "%20"));
		sb.append("&doktyp=mot&rm=");
		sb.append(rm);
		sb.append("&from=");
		sb.append(period.getFrom());
		sb.append("&tom=");
		sb.append(period.getTo());
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
