/**
 * 
 */
package com.searcheveryaspect.backend.database.read;

import com.google.common.base.MoreObjects;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Contains information about what the ESQuerier will search for. Get search info from
 * frontend. Creates a querier depending on information in fields and querier returns the
 * result. Then creates a Response depending on the search result.
 * 
 * @author Mitra
 * 
 */
public class ESRequest {
  private final List<String> party;
  private final Interval interval;
  private final String period;
  private final List<String> category;


  public ESRequest(Interval interval, List<String> category, List<String> party, String period) {
    this.interval = interval;
    this.category = category;
    this.party = party;
    this.period = period;
  }

  public ESRequest(Interval interval, List<String> category, String period) {
    this.interval = interval;
    this.category = category;
    this.party = new ArrayList<String>();
    this.period = period;
  }

  public List<String> getParty() {
    return party;
  }

  public Interval getInterval() {
    return interval;
  }

  public List<String> getCategory() {
    return category;
  }

  public String getPeriod() {
    return period;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ESRequest) {
      ESRequest that = (ESRequest) o;
      return party.equals(that.party) && interval.equals(that.interval)
          && period.equals(that.period) && category.equals(that.category);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(party, interval, period, category);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("party", party).add("interval", interval)
        .add("period", period).add("category", category).toString();
  }
}
