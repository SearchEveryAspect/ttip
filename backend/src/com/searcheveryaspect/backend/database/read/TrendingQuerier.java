package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.shared.Category;
import com.searcheveryaspect.backend.shared.Party;
import com.searcheveryaspect.backend.webserver.PartyData;
import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

/**
 * Trending Querier implements the DatabaseReader interface and performs database searches
 * for the top n trending categories for the last month.
 */
public final class TrendingQuerier implements DatabaseReader<TrendingRequest, SearchResponse> {
  private final Client client;

  private TrendingQuerier(Client client) {
    this.client = checkNotNull(client);
  }

  public SearchResponse read(TrendingRequest input) {
    SearchRequest request = new SearchRequest("motions");

    for (Category cat : Category.values()) {
      // TODO: call processCategory and then aggregate data.
    }

    // Working search for ES with party, category and time interval. Kept atm as an
    // example.
    org.elasticsearch.action.search.SearchResponse response =
        client
            // Set index
            .prepareSearch("motions")
            // Set type.\
            .setTypes("motion")
            // Specify party
            .setQuery(QueryBuilders.matchQuery("party", "MP"))
            // Specify category
            .setQuery(QueryBuilders.matchQuery("category", "unknown"))
            // Set time interval
            .setPostFilter(
                FilterBuilders.rangeFilter("publishedTimestamp").from(1395788500).to(1398636000))
            .execute().actionGet();

    System.out.println(response.toString() + "\n" + response.getHits() + "\n"
        + response.getHeaders());
    return null;
  }

  public static DatabaseReader<TrendingRequest, SearchResponse> newReader(Client client) {
    return new TrendingQuerier(client);
  }

  private SearchResponse processCategory(Category cat) {
    List<PartyData> partyData = null;
    for (Party party : Party.values()) {
      partyData.add(processParty(cat, party));

    }
    for (PartyData p : partyData) {
      // TODO:find best matches.
    }
    // TODO: parse into a SearchResponse.
    return null;
  }

  private PartyData processParty(Category category, Party party) {
    PartyData partyData = null;
    // TODO: Get for correct time period.
    org.elasticsearch.action.search.SearchResponse response =
        client.prepareSearch("motions").setTypes("motion")
            .setQuery(QueryBuilders.matchQuery("party", party.toString()))
            .setQuery(QueryBuilders.matchQuery("category", category.toString())).execute()
            .actionGet();
    // TODO: parse response into PartyData object.
    // TODO: Add search for last year and return the relation value.
    return partyData;
  }
}
