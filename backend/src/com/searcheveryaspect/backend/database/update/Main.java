package com.searcheveryaspect.backend.database.update;

import com.beust.jcommander.JCommander;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.util.List;

/**
 * Runs an update request for the database with motions documents between the dates
 * supplied in flag -from and -to. Currently this specifies only year.
 */
public class Main {

  public static void main(String[] args) {
    CommandLineArgs cla = new CommandLineArgs();
    new JCommander(cla, args);
    String from = cla.from;
    String to = cla.to;

    Node node = NodeBuilder.nodeBuilder().client(true).node().start();
    Client client = node.client();
    ElasticSearchPut db = new ElasticSearchPut(client);

    // TODO: Use jodatime and set complete date in flag.
    GovFetchRequest request =
        GovFetchRequest
            .newGovFetchRequest()
            .period(
                new Period(new GovDate(Integer.parseInt(from), 1, 1), new GovDate(Integer
                    .parseInt(to), 5, 1))).build();
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
        // System.out.println(doc);
        db.putDocument(doc);
      }
    }

    client.close();
    node.close();
  }
}
