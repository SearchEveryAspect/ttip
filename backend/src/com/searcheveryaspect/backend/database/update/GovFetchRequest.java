package com.searcheveryaspect.backend.database.update;


import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Represents a fetch request to Oppna Data API.
 * 
 */
public class GovFetchRequest {

  private final String RIKSDAG_URL = "http://data.riksdagen.se/dokumentlista/";
  // Sökord
  private final String searchString;
  // Riksmöte
  private final String rm;

  private final Period period;
  // Systemdatum
  private final String ts;
  // Beteckning
  private final String bet;
  // Tempbet
  private final String tempbet;
  // Nummer
  private final String nr;
  // Utskott/Organ
  private final String organ;
  // Ledamot
  private final int commissionerId; // -1 if not used
  // List of different parties
  private final ImmutableList<String> parties;

  public GovFetchRequest(String searchString, String rm, Period period, String ts, String bet,
      String tempbet, String nr, String organ, int commissionerId, List<String> parties) {
    this.searchString = searchString;
    this.rm = rm;
    this.period = period;
    this.ts = ts;
    this.bet = bet;
    this.tempbet = tempbet;
    this.nr = nr;
    this.organ = organ;
    this.commissionerId = commissionerId;
    this.parties = ImmutableList.copyOf(parties);
  }

  public GovFetchRequest(String searchString, String rm, Period period, String ts, String bet,
      String tempbet, String nr, String organ, List<String> parties) {
    this.searchString = searchString;
    this.rm = rm;
    this.period = period;
    this.ts = ts;
    this.bet = bet;
    this.tempbet = tempbet;
    this.nr = nr;
    this.organ = organ;
    this.commissionerId = -1;
    this.parties = ImmutableList.copyOf(parties);
  }

  private GovFetchRequest(Builder b) {
    this.searchString = b.searchString;
    this.rm = b.rm;
    this.period = b.period;
    this.ts = b.ts;
    this.bet = b.bet;
    this.tempbet = b.tempbet;
    this.nr = b.nr;
    this.organ = b.organ;
    this.commissionerId = b.commissionerId;
    this.parties = ImmutableList.copyOf(b.parties);
  }

  public String getRIKSDAG_URL() {
    return RIKSDAG_URL;
  }

  public String getSearchString() {
    return searchString;
  }

  public String getRm() {
    return rm;
  }

  public Period getPeriod() {
    return period;
  }

  public String getTs() {
    return ts;
  }

  public String getBet() {
    return bet;
  }

  public String getTempbet() {
    return tempbet;
  }

  public String getNr() {
    return nr;
  }

  public String getOrgan() {
    return organ;
  }

  public int getCommissionerId() {
    return commissionerId;
  }

  public ImmutableList<String> getParties() {
    return parties;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(RIKSDAG_URL);
    sb.append("?sok=");
    sb.append(searchString.replace(" ", "%20"));
    sb.append("&doktyp=mot&rm=");
    sb.append(rm);
    sb.append("&from=");
    sb.append(period.getFrom());
    sb.append("&tom=");
    sb.append(period.getTo());
    sb.append("&ts=");
    sb.append(ts);
    sb.append("&bet=");
    sb.append(bet);
    sb.append("&tempbet=");
    sb.append(tempbet);
    sb.append("&nr=");
    sb.append("");
    sb.append("&org=");
    sb.append(organ);
    sb.append("&iid=");
    if (commissionerId != -1) sb.append(commissionerId);
    for (int i = 0; i < parties.size(); i++) {
      sb.append("&parti=");
      sb.append(parties.get(i));
    }
    sb.append("&webbtv=&talare=&exakt=&planering=&sort=rel&sortorder=desc&rapport=&utformat=json&a=s#soktraff");
    return sb.toString();
  }

  private static class Builder {
    private String searchString;
    // Riksmöte
    private String rm;

    private Period period;
    // Systemdatum
    private String ts;
    // Beteckning
    private String bet;
    // Tempbet
    private String tempbet;
    // Nummer
    private String nr;
    // Utskott/Organ
    private String organ;
    // Ledamot
    private int commissionerId; // -1 if not used
    // List of different parties
    private List<String> parties;

    public Builder searchString(String s) {
      searchString = s;
      return this;
    }

    public Builder riksmote(String s) {
      rm = s;
      return this;
    }

    public Builder period(Period p) {
      period = p;
      return this;
    }

    public Builder systemDate(String s) {
      ts = s;
      return this;
    }

    public Builder beteckning(String s) {
      bet = s;
      return this;
    }

    public Builder tempBeteckning(String s) {
      tempbet = s;
      return this;
    }

    public Builder number(String s) {
      nr = s;
      return this;
    }

    public Builder organ(String s) {
      organ = s;
      return this;
    }

    public Builder tempBeteckning(int i) {
      commissionerId = i;
      return this;
    }

    public Builder parties(List<String> l) {
      parties = l;
      return this;
    }

    public GovFetchRequest build() {
      return new GovFetchRequest(this);
    }
  }

  public static Builder newGovFetchRequest() {
    return new Builder();
  }
}
