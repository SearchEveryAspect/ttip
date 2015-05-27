package com.searcheveryaspect.backend.shared;


/**
 * All categories that can be used to classify a motion. Currently incomplete list.
 */
public enum Category {
  SKATT("skatt"),
  FORSVAR("försvar"),
  MILJO("miljö"),
  VARD("vård"),
  UTBILDNING("utbildning"),
  EKONOMI("ekonomi"),
  MIGRATION("migration"),
  UTRIKESPOLITIK("utrikespolitik"),
  TRAFIK("trafik"),
  ARBETSMARKNAD("arbetsmarknad"),
  KULTUR("kultur"),
  JORDBRUK("jordbruk"),
  NARINGSPOLITIK("näringspolitik"),
  ENERGIPOLITIK("energipolitik"),
  LAGOCHRATT("lag och rätt"),
  BOSTAD("bostad"),
  SOCIALFORSAKRING("socialförsäkring"),
  ALKOHOLOCHDROGER("alkohol och droger"),
  FORSKNING("forskning"),
  UNKNOWN("unknown");
  
  
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
