package com.searcheveryaspect.backend.database.update;

import com.google.gson.Gson;

import com.beust.jcommander.JCommander;
import com.searcheveryaspect.backend.shared.ESUpdateInfo;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

/**
 * Runs an update request for the database with motions documents between the dates
 * supplied in flag -from and -to. Currently this specifies only year.
 */
public class UpdateDatabaseMain {
	
	static final Logger logger = Logger.getLogger("updateDatabaseLogger.UpdateDatabaseMain");

  public static void main(String[] args) {
    CommandLineArgs cla = new CommandLineArgs();
    new JCommander(cla, args);

    // Published interval for motions to be updated.
    DateTime start = DateTime.parse(cla.from, DateTimeFormat.forPattern("yyyy-MM-dd"));
    DateTime end = DateTime.parse(cla.to, DateTimeFormat.forPattern("yyyy-MM-dd"));
    Interval interval = new Interval(start, end);

    // Create a new Elasticsearch node and client and hand to the db connection.
    Node node = NodeBuilder.nodeBuilder().client(true).node().start();
    Client client = node.client();
    ElasticSearchPut db = new ElasticSearchPut(client);
    
    ESDocumentBuilder.initBuilder();

    GovFetchRequest request = GovFetchRequest.newGovFetchRequest().interval(interval).build();
    List<GovDocumentList> docs = null;

    try {
      docs = GovClient.fetchDocs(request);
    } catch (Exception e) {
      logger.error("Failed to fetchdocs", e);
      client.close();
      node.close();
      System.exit(0);
    }

    ESDocumentBuilder.initBuilder();
    for (GovDocumentList govDocumentList : docs) {
      GovDocument[] govDocuments = govDocumentList.dokument;
      if (govDocuments == null) {
        continue;
      }
      for (GovDocument govDocument : govDocuments) {
        try {
          ESDocument doc = ESDocumentBuilder.createESDocument(new GovDocumentLite(govDocument));
          db.putDocument(doc);
        } catch (NullPointerException | IllegalArgumentException e) {
        	logger.error("Document ignored, " , e);
      
        }
      }
    }
    setUpdateTimestamp(client, DateTime.now());

    client.close();
    node.close();
  }

  /**
   * Updates the provided client with the provided timestamp. The timestamp is stored in
   * index motion, type updated, id 1. If a previous timestamp exists it will replace that
   * one.
   * 
   * @param client
   * @param ts
   */
  private static void setUpdateTimestamp(Client client, DateTime ts) {
    Gson gson = new Gson();
    String json = gson.toJson(new ESUpdateInfo(ts));
    client.prepareIndex("system", "updated", "1").setSource(json).execute().actionGet();
  }
}
