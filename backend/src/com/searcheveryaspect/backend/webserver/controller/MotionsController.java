package com.searcheveryaspect.backend.webserver.controller;

import com.beust.jcommander.Parameter;
import com.searcheveryaspect.backend.ESQuerier;
import com.searcheveryaspect.backend.ESRequest;
import com.searcheveryaspect.backend.webserver.SearchAggregateResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.joda.time.Interval;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 * Processes read requests for real motions within a period.
 */
public class MotionsController extends ReadOnlyController {
  private ESQuerier esq;



  /**
   * Returns a new MotionsController. The ESQuerier serves as a connection to the database
   * and is needed to process read requests.
   */
  public MotionsController(ESQuerier esq) {
    this.esq = esq;
  }


  public SearchAggregateResponse read(Request request, Response response) {
    recordForDebug(request, response);
    try {
      final ESRequest esreq = parseRequest(request);

      // Restexpress doesn't really support asynchronous controllers so we can use
      // futures to detach the controller from the backend thread.
      ListenableFuture<SearchAggregateResponse> future =
          MoreExecutors.newDirectExecutorService().submit(new Callable<SearchAggregateResponse>() {
            // Pass on the request to instance of ESQuerier.
            @Override
            public SearchAggregateResponse call() throws Exception {
              return esq.fetchDocuments(esreq);
            }
          });

      return future.get();
    } catch (Exception e) {
      response.setException(e);
      return null;
    }
  }

  private ESRequest parseRequest(Request request) throws IllegalArgumentException {
    final String fromDate = (String) request.getHeader("from_date");
    final String toDate = (String) request.getHeader("to_date");
    final String category = (String) request.getHeader("category");
    final String period = (String) request.getHeader("interval");
    if (fromDate == null || toDate == null || category == null || category == null) {
      throw new IllegalArgumentException("All request parameters aren't specified");
    }

    DateTime start = DateTime.parse(fromDate, DateTimeFormat.forPattern("yyyy-mm-dd"));
    DateTime end = DateTime.parse(toDate, DateTimeFormat.forPattern("yyyy-mm-dd"));

    if (start.compareTo(end) > 0) {
      throw new IllegalArgumentException("Request parameter from is before parameter to");
    }

    Interval interval = new Interval(start, end);

    List<String> cats = new ArrayList<String>();
    cats.add(category);
    return new ESRequest(interval, cats, period);
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
}
