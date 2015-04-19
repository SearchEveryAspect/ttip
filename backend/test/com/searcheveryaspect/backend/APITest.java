/**
 * 
 */
package com.searcheveryaspect.backend;

import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.*;

/**
 * @author dajmmannen
 *
 */
public final class APITest 
{

	public static ArrayList<GovDocumentList> QueryAPI(String search, int startYear, int endYear, ArrayList<String> parties) throws Exception
	{
		GovClient gv = new GovClient();
		
		GovFetchRequest request = new GovFetchRequest(search, "", new Period(new GovDate(startYear, 1, 1), new GovDate(endYear, 12, 31)), "", "", "", "", "", parties);
		return gv.fetchDocs(request);
	}
	
	
}
