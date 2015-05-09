package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.webserver.controller.SystemResponse;

import org.elasticsearch.client.Client;

/**
 * SystemQuerier performs the necessary database reads for system meta information
 * requests.
 */
public class SystemQuerier implements DatabaseReader<SystemRequest, SystemResponse> {
  private final Client client;

  private SystemQuerier(Client client) {
    this.client = checkNotNull(client);
  }

  public static DatabaseReader<SystemRequest, SystemResponse> newReader(Client client) {
    return new SystemQuerier(client);
  }

  public SystemResponse read(SystemRequest input) {
    return null;
  }
}
