/**
 * 
 */
package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;

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
		SearchRequest request = new SearchRequest("motions");

		//Search for JSONs that match req.category and req.Interval
		if(req.getCategory() != null) {
			org.elasticsearch.action.search.SearchResponse response = regularSearch(req);	
		}
		else {
			org.elasticsearch.action.search.SearchResponse response = categorySearch(req);
			//where categorySearch gets all the categories in the database
		}
		
		//call a method that creates "an aggregateresponse"..
		
		return null;
	}
	
	/**
	 * Search on category and Interval
	 * @param req The ES request
	 * @return response from ES
	 */
	private org.elasticsearch.action.search.SearchResponse regularSearch(ESRequest req) {
		org.elasticsearch.action.search.SearchResponse response = 
				client
						.prepareSearch("motions")
						.setTypes("motion")
						.setQuery(QueryBuilders.matchQuery("category", req.getCategory()))
						.setPostFilter(FilterBuilders.rangeFilter("publishedTimestamp").from(req.getInterval().getStart()).to(req.getInterval().getEnd()))
						.execute().actionGet();
		
		return response;
	}
	
	private org.elasticsearch.action.search.SearchResponse categorySearch(ESRequest req) {
		org.elasticsearch.action.search.SearchResponse response = 
				client
						.prepareSearch("motions")
						.setTypes("motion")
						.setQuery(QueryBuilders.matchAllQuery())
						.addAggregation(AggregationBuilders.terms("categories").field("category").size(200)) //TODO find a nicer solution to get all results...
						.execute().actionGet();
		return response;
	}

	public static DatabaseReader<ESRequest, SearchResponse> newReader(Client client) {
		return new ESQuerier(client);
	}
}
