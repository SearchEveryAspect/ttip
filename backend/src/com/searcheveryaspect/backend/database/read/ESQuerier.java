/**
 * 
 */
package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.elasticsearch.client.Client;

/**
 * Communicates with ES depending on what kind of information the ESRequest object
 * contains.
 * 
 * @author Mitra
 * 
 */
public final class ESQuerier implements DatabaseReader<ESRequest, SearchResponse> {
  private final Client client;

  private ESQuerier(Client client) {
    this.client = checkNotNull(client);
  };

  /**
   * Queries the database as specified by the ESRequest and returns a SearchResponse.
   */
  public SearchResponse read(ESRequest req) {
    return null;
  }

  public static DatabaseReader<ESRequest, SearchResponse> newReader(Client client) {
    return new ESQuerier(client);
  }
}
