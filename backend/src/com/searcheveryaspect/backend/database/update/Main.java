package com.searcheveryaspect.backend.database.update;

import com.beust.jcommander.JCommander;

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
public class Main {

  public static void main(String[] args) {
    CommandLineArgs cla = new CommandLineArgs();
    new JCommander(cla, args);

    // Published interval for motions to be updated.
    DateTime start = DateTime.parse(cla.from, DateTimeFormat.forPattern("yyyy-mm-dd"));
    DateTime end = DateTime.parse(cla.to, DateTimeFormat.forPattern("yyyy-mm-dd"));
    Interval interval = new Interval(start, end);

    // Create a new Elasticsearch node and client and hand to the db connection.
    Node node = NodeBuilder.nodeBuilder().client(true).node().start();
    Client client = node.client();
    ElasticSearchPut db = new ElasticSearchPut(client);

    GovFetchRequest request = GovFetchRequest.newGovFetchRequest().interval(interval).build();
    List<GovDocumentList> docs = null;

    try {
      docs = GovClient.fetchDocs(request);
    } catch (Exception e) {
      e.printStackTrace();
      client.close();
      node.close();
      System.exit(0);
    }

    for (GovDocumentList govDocumentList : docs) {
      GovDocument[] govDocuments = govDocumentList.dokument;
      for (GovDocument govDocument : govDocuments) {
        ESDocument doc = ESDocumentBuilder.createESDocument(new GovDocumentLite(govDocument));
        db.putDocument(doc);
      }
    }

    client.close();
    node.close();
  }
}
