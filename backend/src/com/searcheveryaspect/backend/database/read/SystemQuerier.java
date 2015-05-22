package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.webserver.SystemResponse;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.joda.time.DateTime;

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
    return new SystemResponse(getLastUpdated());
  }

  private DateTime getLastUpdated() {
    GetResponse response = client.prepareGet("system", "updated", "1").execute().actionGet();
    DateTime ts = new DateTime(response.getField("ts").getValue());
    return ts;
  }
}
