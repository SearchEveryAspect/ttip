package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.RestExpress;
import org.restexpress.exception.ServiceException;
import org.restexpress.response.ErrorResponseWrapper;
import org.restexpress.serialization.AbstractSerializationProvider;
import org.restexpress.serialization.json.JacksonJsonProcessor;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 
 */
public class Main {

  public static void main(String[] args) {
    RestExpress.setSerializationProvider(new AbstractSerializationProvider() {
      {
        add(new JacksonJsonProcessor(), new ErrorResponseWrapper(), true);
      }
    });
    RestExpress server = new RestExpress().setName("SEA").setPort(12345);

    defineRoutes(server);

    server.setIoThreadCount(4);
    server.setExecutorThreadCount(4);

    mapExceptions(server);
    server.bind();
    server.awaitShutdown();
  }

  private static void defineRoutes(RestExpress server) {
    server.uri("/mot/{category}/from/{from_date}/to/{to_date}", new ReadMotionsController())
        .noSerialization();
  }

  static abstract class ReadOnlyController {
    public void create(Request request, Response response) {

    }

    public void update(Request request, Response response) {

    }

    public void delete(Request request, Response response) {

    }
  }

  static class ReadMotionsController extends ReadOnlyController {


    public String read(Request request, Response response) {
      recordForDebug(request, response);
      try {
        String fromDate = (String) request.getHeader("from_date");
        String toDate = (String) request.getHeader("to_date");
        String category = (String) request.getHeader("category");
        if (fromDate == null || toDate == null || category == null) {
          throw new IllegalArgumentException("You fail sir!");
        }

        DateTime fromDateInstant =
            DateTime.parse(fromDate, DateTimeFormat.forPattern("yyyy-mm-dd"));
        DateTime toDateInstant = DateTime.parse(toDate, DateTimeFormat.forPattern("yyyy-mm-dd"));

        // Restexpress doesn't really support asynchronous controllers so we can use
        // futures to detach the controller from the backend thread.
        ListenableFuture<String> future =
            MoreExecutors.newDirectExecutorService().submit(new Callable<String>() {

              @Override
              public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                return "OMG!";
              }
            });

        return "Result:" + future.get() + ">>>"
            + DateTimeFormat.fullDateTime().print(fromDateInstant) + " - "
            + DateTimeFormat.fullDateTime().print(toDateInstant);
      } catch (Exception e) {
        return Throwables.getStackTraceAsString(e);
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
  }

  private static void mapExceptions(RestExpress server) {
    server.mapException(RuntimeException.class, ServiceException.class);
  }

}
