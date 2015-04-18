package com.searcheveryaspect.backend;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import com.google.gson.Gson;

/**
 * 
 * @author Jacqueline Eriksson
 * @version 2015-04-18
 */

//http://www.efroot.org/2013/04/indexing-into-elasticsearch.html
//ska man använda samma klient i hela applikationen el ska mitra skapa egen? 
//It's definitely better if you can share the same client instance for your whole application.
//So don't open a new node and a new client each time you need it. Share it (use a factory).

public class ElasticSearchPut {
	

	
	node = NodeBuilder.nodeBuilder().client(true).node();
	Client client = node.client();
	

	//TODO: För alla dokument{
	public void putDocument(ESDocument doc) {

	    Gson gson = new Gson(); 
	    String jsonString = gson.toJson(doc);    
	    client.prepareIndex("motions", "motion", doc.getDocId())
	                            .setSource(jsonString)
	                            .execute()
	                            .actionGet();
	}
	//}
	
	client.close();
	node.close();
	    
	
}
/**
 * 
 *

	Client client = NodeBuilder.nodeBuilder().
								client(true).
								node().
								client();

	boolean indexExists = client.admin().indices().prepareExists(INDEX).execute().actionGet().isExists();
	if (indexExists) {
		client.admin().indices().prepareDelete(INDEX).execute().actionGet();
	}
	client.admin().indices().prepareCreate(INDEX).execute().actionGet();
	
	SearchResponse allHits = client.prepareSearch(Indexer.INDEX).addFields("title", "category").setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();





//LÄS
http://www.kb.se/dokument/Bibliotek/projekt/Slutrapport%202013/OCR/OCR-spec%20(2).pdf
	http://www.ibm.com/developerworks/library/j-javadev2-24/
		https://templth.wordpress.com/2015/01/23/implementing-integration-testing-for-elasticsearch-with-java/
			https://www.airpair.com/elasticsearch/posts/elasticsearch-robust-search-functionality

				http://www.javased.com/


					//ska här tillverka Json document och skicka in det i elastic search 
					public void putDocument(ESDocument doc) {

	http://www.elastic.co/guide/

		Java api 
		http://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html
			http://java.dzone.com/articles/elasticsearch-java-api
				http://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index_.html

					http://stackoverflow.com/questions/19845929/elasticsearch-java-api-index-document

						//fixa klient 
						https://found.no/foundation/java-clients-for-elasticsearch/
						https://github.com/hugovalk/elasticsearch-java-demo/tree/master/src/main/java/com/devdiscoveries/elasticsearch/demo/client
						http://www.elastic.co/guide/en/elasticsearch/guide/master/_talking_to_elasticsearch.html
							http://www.elastic.co/guide/en/elasticsearch/client/java-api/current/client.html#transport-client
								https://www.found.no/foundation/java-clients-for-elasticsearch/

									//index
									https://github.com/jaibeermalik/elasticsearch-tutorial/blob/master/src/main/java/org/jai/search/setup/IndexSchemaBuilder.java

										ElasticSearchIndexConfig config = ElasticSearchIndexConfig.COM_WEBSITE;

Long productId = 123456l;
assertFalse(indexProductData.isProductExists(config, productId));

Product product = new Product();
product.setId(productId);
product.setTitle("blah blah");
product.setPrice(BigDecimal.valueOf(5));
product.setAvailableOn(new Date());

indexProductData.indexProduct(config, product);


//
http://stackoverflow.com/questions/8154902/elasticsearch-insert-objects-into-index
	Tweet tweet = new Tweet();
tweet.setId("1234");
tweet.setMessage("message");

IndexRequest indexRequest = new IndexRequest("twitter","tweet", tweet.getId());
indexRequest.source(new Gson().toJson(tweet));
IndexResponse response = client.index(indexRequest).actionGet();

//
UpdateRequest updateRequest = new UpdateRequest();
updateRequest.index("index");
updateRequest.type("type");
updateRequest.id("1");
updateRequest.doc(jsonBuilder()
		.startObject()
		.field("gender", "male")
		.endObject());
client.update(updateRequest).get();

Gson gson = new Gson();
String json = gson.toJson(doc);
IndexResponse response = client.prepareIndex("twitter", "tweet")
.setSource(json)
.execute()
.actionGet();


//exempelkod
https://github.com/searchbox-io/Jest/blob/master/jest/src/test/java/io/searchbox/core/BulkIntegrationTest.java
	http://www.programcreek.com/java-api-examples/index.php?api=org.elasticsearch.action.bulk.BulkRequestBuilder


		//1 way I can get the api to work is by not using it:
		http://stackoverflow.com/questions/19845929/elasticsearch-java-api-index-document

			//innehåller länkar 
			http://www.ibm.com/developerworks/library/j-javadev2-24/
				
				//kan vara bra 
				https://groups.google.com/forum/#!forum/elasticsearch

}

}
*/
