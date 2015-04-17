package com.searcheveryaspect.backend.webserver.controller;

import com.searcheveryaspect.backend.webserver.SearchAggregateResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 * 
 */
public class MotionsController extends ReadOnlyController {


  public SearchAggregateResponse read(Request request, Response response) {
    recordForDebug(request, response);
    try {
      final String fromDate = (String) request.getHeader("from_date");
      final String toDate = (String) request.getHeader("to_date");
      final String category = (String) request.getHeader("category");
      final String interval = (String) request.getHeader("interval");
      if (fromDate == null || toDate == null || category == null || category == null) {
        throw new IllegalArgumentException("You fail sir!");
      }

      DateTime fromDateInstant = DateTime.parse(fromDate, DateTimeFormat.forPattern("yyyy-mm-dd"));
      DateTime toDateInstant = DateTime.parse(toDate, DateTimeFormat.forPattern("yyyy-mm-dd"));

      // Restexpress doesn't really support asynchronous controllers so we can use
      // futures to detach the controller from the backend thread.
      ListenableFuture<SearchAggregateResponse> future =
          MoreExecutors.newDirectExecutorService().submit(new Callable<SearchAggregateResponse>() {
            // TODO(Jenny): Use with actual search in database.



            @Override
            public SearchAggregateResponse call() throws Exception {
              TimeUnit.SECONDS.sleep(5);
              if (interval.equals("month")) {
                return dummyMonthResponse();
              }
              return dummyYearResponse();
            }
          });

      return future.get();
    } catch (Exception e) {
      return null;
      // return Throwables.getStackTraceAsString(e);
    }
  }

  void recordForDebug(Request request, Response response) {
    if (request.getHeader("debug") != null) {
      try {
        boolean debug = Boolean.parseBoolean(request.getHeader("debug"));

        if (debug) {

          System.out.println(MoreObjects.toStringHelper(Request.class)
              .add("path", request.getPath()).toString()
              + " " + response);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static SearchAggregateResponse dummyMonthResponse() {
    ImmutableList<String> labels =
        new ImmutableList.Builder<String>().add("December 2014").add("Januari 2015")
            .add("Februari 2015").build();
    ImmutableList<SearchAggregateResponse.PartyData> dataset =
        new ImmutableList.Builder<SearchAggregateResponse.PartyData>()
            .add(
                new SearchAggregateResponse.PartyData("S", new ImmutableList.Builder<Integer>()
                    .add(3).add(4).add(5).build()))
            .add(
                new SearchAggregateResponse.PartyData("M", new ImmutableList.Builder<Integer>()
                    .add(8).add(4).add(2).build())).build();
    return SearchAggregateResponse.newSearchAggregateResponse().category("skatt").labels(labels)
        .datasets(dataset).build();
  }

  private static SearchAggregateResponse dummyYearResponse() {
    ImmutableList<String> labels =
        new ImmutableList.Builder<String>().add("2010").add("2011").add("2012").add("2013")
            .add("2014").build();
    ImmutableList<SearchAggregateResponse.PartyData> dataset =
        new ImmutableList.Builder<SearchAggregateResponse.PartyData>()
            .add(
                new SearchAggregateResponse.PartyData("V", new ImmutableList.Builder<Integer>()
                    .add(34).add(10).add(24).add(25).add(19).build()))
            .add(
                new SearchAggregateResponse.PartyData("C", new ImmutableList.Builder<Integer>()
                    .add(11).add(45).add(43).add(19).add(21).build())).build();
    return SearchAggregateResponse.newSearchAggregateResponse().category("försvar").labels(labels)
        .datasets(dataset).build();
  }
}
