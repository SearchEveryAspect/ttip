package com.searcheveryaspect.backend;

import com.google.gson.Gson;

public class ElasticSearchPut {
	
	//prio 1
	https://groups.google.com/forum/#!topic/elasticsearch/BeF7esI0dS8
		https://groups.google.com/forum/#!forum/elasticsearch
	
	//LÄS
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
			http://www.elastic.co/guide/en/elasticsearch/guide/master/_talking_to_elasticsearch.html
		http://www.elastic.co/guide/en/elasticsearch/client/java-api/current/client.html#transport-client
		
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
				
	}

}
