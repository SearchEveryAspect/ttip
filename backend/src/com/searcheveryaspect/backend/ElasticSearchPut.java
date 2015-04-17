package com.searcheveryaspect.backend;

import com.google.gson.Gson;

public class ElasticSearchPut {
	
	
	//ska här tillverka Json document och skicka in det i elastic search 
	public void putDocument(ESDocument doc) {
		
		http://www.elastic.co/guide/
			
		Java api 
		http://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html
		
		http://stackoverflow.com/questions/19845929/elasticsearch-java-api-index-document
		
		//fixa klient 
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
		
	}

}
