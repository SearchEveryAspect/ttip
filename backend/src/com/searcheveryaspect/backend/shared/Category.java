package com.searcheveryaspect.backend.shared;


/**
 * All categories that can be used to classify a motion. Currently incomplete list.
 */
public enum Category {
  SKATT("skatt"), FORSVAR("försvar"), MILJO("miljö");
  private String prettyName;

  private Category(String prettyName) {
    this.prettyName = prettyName;
  }

  /**
   * Returns a lower case string of the enum.
   */
  @Override
  public String toString() {
    return prettyName;
  }
}
