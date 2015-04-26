package com.searcheveryaspect.backend;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.webserver.TrendingResponse;

import org.elasticsearch.client.Client;

/**
 * 
 */
public final class TrendingQuerier implements DatabaseReader<TrendingRequest, TrendingResponse> {
  private final Client client;

  private TrendingQuerier(Client client) {
    this.client = checkNotNull(client);
  }

  public TrendingResponse read(TrendingRequest input) {
    return null;
  }

  public static DatabaseReader<TrendingRequest, TrendingResponse> newReader(Client client) {
    return new TrendingQuerier(client);
  }
}
