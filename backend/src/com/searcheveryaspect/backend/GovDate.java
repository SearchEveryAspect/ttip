package com.searcheveryaspect.backend;

public class GovDate
{
	int year;
	int month;
	int day;
	
	public GovDate(int year, int month, int day)
	{
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public String toString()
	{
		return year + "-" + ((month >= 10) ? month : ("0" + month)) + "-"
	+ ((day >= 10) ? day : ("0" + day));
	}
	
}
