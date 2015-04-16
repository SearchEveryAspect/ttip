package com.searcheveryaspect.backend.webserver;

import com.searcheveryaspect.backend.webserver.controller.MotionsController;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.RestExpress;
import org.restexpress.exception.ServiceException;
import org.restexpress.response.ErrorResponseWrapper;
import org.restexpress.serialization.AbstractSerializationProvider;
import org.restexpress.serialization.json.JacksonJsonProcessor;

/**
 * Starts up the server offering up the ttip API.
 */
public class Main {

  public static void main(String[] args) {

    RestExpress.setSerializationProvider(new AbstractSerializationProvider() {
      {
        add(new JacksonJsonProcessor(), new ErrorResponseWrapper(), true);
      }
    });
    RestExpress server = new RestExpress().setName("SEA").setPort(8080);

    defineRoutes(server);

    server.setIoThreadCount(4);
    server.setExecutorThreadCount(4);

    mapExceptions(server);
    server.bind();
    server.awaitShutdown();
  }

  // The allowed routes the server responds to.
  private static void defineRoutes(RestExpress server) {
    server.uri("/mot/{category}/{interval}/from/{from_date}/to/{to_date}", new MotionsController());
  }

  private static void mapExceptions(RestExpress server) {
    server.mapException(RuntimeException.class, ServiceException.class);
  }
}
