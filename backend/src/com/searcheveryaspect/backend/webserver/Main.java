package com.searcheveryaspect.backend.webserver;

import com.searcheveryaspect.backend.webserver.controller.MotionsController;

import com.beust.jcommander.JCommander;
import org.restexpress.RestExpress;
import org.restexpress.exception.ServiceException;
import org.restexpress.response.ErrorResponseWrapper;
import org.restexpress.serialization.AbstractSerializationProvider;
import org.restexpress.serialization.json.JacksonJsonProcessor;

/**
 * Start a ttip webserver. Flag -port sets port for traffic, default is 8080.
 */
public class Main {

  public static void main(String[] args) {
    CommandLineArgs cla = new CommandLineArgs();
    new JCommander(cla, args);

    RestExpress.setSerializationProvider(new AbstractSerializationProvider() {
      {
        add(new JacksonJsonProcessor(), new ErrorResponseWrapper(), true);
      }
    });
    RestExpress server = new RestExpress().setName("SEA").setPort(cla.port.intValue());

    defineRoutes(server);

    server.setIoThreadCount(4);
    server.setExecutorThreadCount(4);

    mapExceptions(server);
    server.bind();
    server.awaitShutdown();
  }

  private static void defineRoutes(RestExpress server) {
    server.uri("/mot/{category}/{interval}/from/{from_date}/to/{to_date}", new MotionsController());
  }

  private static void mapExceptions(RestExpress server) {
    server.mapException(RuntimeException.class, ServiceException.class);
  }
}
