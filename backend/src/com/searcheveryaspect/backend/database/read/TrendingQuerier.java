package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import com.searcheveryaspect.backend.database.update.ESDocument;
import com.searcheveryaspect.backend.shared.Category;
import com.searcheveryaspect.backend.shared.Party;
import com.searcheveryaspect.backend.webserver.Document;
import com.searcheveryaspect.backend.webserver.Entry;
import com.searcheveryaspect.backend.webserver.PartyData;
import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Iterator;
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
      list.add(processCategory(category, intervals, labels));
    }
    // TODO: Decide on the top n out of the responses.
    return null;
  }

  /**
   * Returns a SearchResponse for the specified category, interval and label containing
   * all parties.
   * 
   * @param category
   * @param intervals
   * @param labels
   * @return
   */
  private SearchResponse processCategory(Category category, List<Interval> intervals,
      ImmutableList<String> labels) {
    List<PartyData> parties = new ArrayList<>();
    for (Party party : Party.values()) {
      parties.add(processParty(category, party, intervals));
    }
    return SearchResponse.newSearchResponse().category(category.toString()).labels(labels)
        .datasets(ImmutableList.copyOf(parties)).build();
  }

  /**
   * Searches and aggregates the category and party data for the specified intervals.
   * 
   * @param category
   * @param party
   * @param intervals
   * @return
   */
  private PartyData processParty(Category category, Party party, List<Interval> intervals) {
    List<Entry> entries = new ArrayList<>();
    for (Interval i : intervals) {
      entries.add(processInterval(category, party, i));
    }
    return new PartyData(party.toString(), ImmutableList.copyOf(entries), false);
  }

  /**
   * Queries the client for all documents with the specified category and party that was
   * published within the interval. If there are no hits it returns an Entry with an
   * initialised list and data of 0.
   * 
   * @param category
   * @param party
   * @param interval
   * @return
   */
  private Entry processInterval(Category category, Party party, Interval interval) {
    org.elasticsearch.action.search.SearchResponse response =
        client
            .prepareSearch("motions")
            .setTypes("motion")
            .setQuery(QueryBuilders.matchQuery("party", party.toString()))
            .setQuery(QueryBuilders.matchQuery("category", category.toString()))
            .setPostFilter(
                FilterBuilders.rangeFilter("publishedTimestamp").from(interval.getStartMillis())
                    .to(interval.getEndMillis())).execute().actionGet();

    // Get the hits as a response
    Iterator<SearchHit> iterator = response.getHits().iterator();
    List<Document> list = new ArrayList<>();

    while (iterator.hasNext()) {
      list.add(new Document(gson.fromJson(iterator.next().getSourceAsString(), ESDocument.class)));
    }

    return new Entry(list.size(), ImmutableList.copyOf(list));
  }
}
