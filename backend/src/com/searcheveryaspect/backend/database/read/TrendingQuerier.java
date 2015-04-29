package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.searcheveryaspect.backend.database.read.QuerierUtil.categorySearch;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import com.searcheveryaspect.backend.shared.Category;
import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.elasticsearch.client.Client;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

/**
 * TrendingQuerier implements the DatabaseReader interface and performs database searches
 * for the top n trending categories for the last month.
 */
public final class TrendingQuerier implements DatabaseReader<TrendingRequest, SearchResponse> {
  private final Client client;
  private final Gson gson;

  private TrendingQuerier(Client client) {
    this.client = checkNotNull(client);
    gson = new Gson();
  }

  /**
   * Use to get a new TrendingQuerier. TrendingQuerier should only be used through the
   * DatabaseReader interface.
   * 
   * @param client
   * @return
   */
  public static DatabaseReader<TrendingRequest, SearchResponse> newReader(Client client) {
    return new TrendingQuerier(client);
  }

  public SearchResponse read(TrendingRequest input) {
    // TODO: create intervals and labels.
    return null;
  }

  private List<SearchResponse> getTopCategories(List<Interval> intervals,
      ImmutableList<String> labels, int n) {
    List<SearchResponse> list = new ArrayList<>();
    for (Category category : Category.values()) {
      list.add(categorySearch(client, category, intervals, labels));
    }
    // TODO: Decide on the top n out of the responses.
    return null;
  }
}
