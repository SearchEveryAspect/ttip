package com.searcheveryaspect.backend.database.read;

/**
 * Request for the top n trending categories.
 */
public class TrendingRequest {
  // The number of top trending categories to return.
  private final int top;

  public TrendingRequest(int top) {
    this.top = top;
  }

  public int getCount() {
    return top;
  }
}
