package com.searcheveryaspect.backend;


public class Period 
{
	GovDate from;
	GovDate to;
	
	public Period(GovDate from, GovDate to)
	{
		this.from = from;
		this.to = to;
	}
	
	public String getFrom()
	{
		return from.toString();
	}
	
	public String getTo()
	{
		return to.toString();
	}
}


