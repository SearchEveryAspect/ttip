package com.searcheveryaspect.backend;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.elasticsearch.client.Client;

/**
 * 
 */
public final class TrendingQuerier implements DatabaseReader<TrendingRequest, SearchResponse> {
  private final Client client;

  private TrendingQuerier(Client client) {
    this.client = checkNotNull(client);
  }

  public SearchResponse read(TrendingRequest input) {
    return null;
  }

  public static DatabaseReader<TrendingRequest, SearchResponse> newReader(Client client) {
    return new TrendingQuerier(client);
  }
}
