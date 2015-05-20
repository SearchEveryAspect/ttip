/**
 * 
 */
package com.searcheveryaspect.backend.database.read;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.joda.time.Interval;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Mitra
 *
 */
public class ESQuerierTest {
	
	@Test
	public void monthIntervalsTest() {
		long startDate = 961526042685L; //2000-06-20 (20:34:02)
		long endDate = 966796253348L;   //2000-08-20 (20:30:53)
		Interval interval = new Interval(startDate, endDate);
		System.out.println(interval.toString());
		
		ArrayList<Interval> intervals = new ArrayList<Interval>();
		try {
			intervals = (ArrayList<Interval>) invMonthIntervals(interval);
			for(Interval i : intervals) {
				System.out.println(i.toString());
			}
		}
		catch(Exception e) {
			System.out.println("\nFailed to invoke method monthIntervals in ESQurier... \n");
		}
		
		
	}
	// Use reflection to invoke private method
	// http://www.java2s.com/Tutorial/Java/0125__Reflection/Callaclassmethodwith2arguments.htm
	private Object invMonthIntervals(Interval interval) throws Exception {
		System.out.println("entered");
		Class<ESQuerier> c = (Class<ESQuerier>) Class.forName("ESQuerier");
		System.out.println("class c");
		Method m = c.getDeclaredMethod("monthIntervals", new Class[] {Interval.class});
		System.out.println("method m");
		ESQuerier i = (ESQuerier) c.newInstance();
		System.out.println("esquerier i");
		ArrayList<Interval> r = (ArrayList<Interval>) m.invoke(interval);
		System.out.println("arraylist r");
		
		//Method method = ESQuerier.getDeclaredMethod("monthIntervals", argClasses);
		//method.setAccessible(true);
		//return method.invoke(targetObject, argObjects);
		
		return r;
	}
	
	@Ignore
	@Test
	public void yearIntervalsTest() {
		
	}

}
