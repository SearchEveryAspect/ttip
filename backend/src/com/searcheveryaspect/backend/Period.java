package com.searcheveryaspect.backend;

import java.util.Date;

public class Period 
{
	GovDate from;
	GovDate to;
	
	public Period(GovDate from, GovDate to)
	{
		this.from = from;
		this.to = to;
	}
	
	@SuppressWarnings("deprecation")
	public String getFrom()
	{
		return from.toString();
	}
	
	@SuppressWarnings("deprecation")
	public String getTo()
	{
		return to.toString();
	}
}


