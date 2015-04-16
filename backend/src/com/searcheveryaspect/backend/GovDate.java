package com.searcheveryaspect.backend;

/**
 * Class for saving dates
*/
public class GovDate
{
	public int year;
	public int month;
	public int day;
	
	private static final int[] MONTH_VALUES = {31, 28, 31, 30, 31, 30 ,31, 31, 30, 31, 30, 31};

	public GovDate(int year, int month, int day)
	{
		
		this.year = year;
		this.month = month;
		while(day > MONTH_VALUES[month - 1])
		{
			month++;
			day = 1;
			while(month > 12)
			{
				year++;
				month = 1;
			}
		}
		while(month > 12)
		{
			year++;
			month = 1;
		}
		
		this.day = day;
	}
	/**
	* Returns date in format YYYY-MM-DD
	**/
	public String toString()
	{
		return year + "-" + ((month >= 10) ? month : ("0" + month)) + "-"
	+ ((day >= 10) ? day : ("0" + day));
	}
	
	public int toInt()
	{
		int yearValue = 365 * year;
		int monthValue = 0;
		
		for(int i = 0; i < month - 1; i++)
		{
			monthValue += MONTH_VALUES[i];
		}
		return yearValue + monthValue + day - 1;
	}
	
	public static GovDate fromInt(int date)
	{
		int year = date / 365;
		
		int remainingInt = date % 365;
		int month = 1;
		
		for(month = 1; month < MONTH_VALUES.length; month++)
		{
			if(remainingInt - MONTH_VALUES[month] < 0)
				break;
			remainingInt -= MONTH_VALUES[month];
		}
		
		int day = remainingInt + 1;
		
		return  new GovDate(year, month, day);
		
	}
	

}
