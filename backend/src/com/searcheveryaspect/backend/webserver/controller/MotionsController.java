package com.searcheveryaspect.backend.webserver.controller;

import com.searcheveryaspect.backend.webserver.SearchResponse;

import java.util.concurrent.Callable;

import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 * MotionsController handles search requests for existing motion for a specified period.
 * It only offers reads.
 */
public class MotionsController extends ReadOnlyController {

  // Implements the Read method for the motions search.
  public SearchResponse read(Request request, Response response) {
    recordForDebug(request, response);
    try {
      final String fromDate = (String) request.getHeader("from_date");
      final String toDate = (String) request.getHeader("to_date");
      final String category = (String) request.getHeader("category");
      // final String interval = (String) request.getHeader("interval");
      if (fromDate == null || toDate == null || category == null || category == null) {
        throw new IllegalArgumentException("You fail sir!");
      }
      DateTime fromDateInstant = DateTime.parse(fromDate, DateTimeFormat.forPattern("yyyy-mm-dd"));
      DateTime toDateInstant = DateTime.parse(toDate, DateTimeFormat.forPattern("yyyy-mm-dd"));

      // Restexpress doesn't really support asynchronous controllers so we can use
      // futures to detach the controller from the backend thread.
      ListenableFuture<SearchResponse> future =
          MoreExecutors.newDirectExecutorService().submit(new Callable<SearchResponse>() {
            // TODO(Jenny): Use with actual search in database.

            @Override
            public SearchResponse call() throws Exception {
              return SearchResponseUtil.dummyMonthResponse(category);
            }
          });

      return future.get();
    } catch (Exception e) {
      return null;
      // return Throwables.getStackTraceAsString(e);
    }
  }

  // A small debugging method that prints out incomming requests to stdout.
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

}
