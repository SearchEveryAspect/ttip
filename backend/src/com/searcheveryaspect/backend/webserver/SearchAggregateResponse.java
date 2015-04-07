package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.Objects;


/**
 * SearchAggregateResponse represent the result of a search for documents within a
 * specific period of time.
 */
public class SearchAggregateResponse {
  private final String category;
  private final ImmutableList<String> labels;
  private final ImmutableList<PartyData> datasets;

  SearchAggregateResponse(Builder builder) {
    this.category = builder.category;
    this.labels = builder.labels;
    this.datasets = builder.datasets;
  }

  public String getCategory() {
    return category;
  }

  public ImmutableList<String> getLabels() {
    return labels;
  }

  public ImmutableList<PartyData> getDatasets() {
    return datasets;
  }

  static class Builder {
    private String category;
    private ImmutableList<String> labels;
    private ImmutableList<PartyData> datasets;

    Builder category(String s) {
      category = s;
      return this;
    }

    Builder labels(ImmutableList<String> l) {
      labels = l;
      return this;
    }

    Builder datasets(ImmutableList<PartyData> l) {
      datasets = l;
      return this;
    }

    SearchAggregateResponse build() {
      return new SearchAggregateResponse(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SearchAggregateResponse) {
      SearchAggregateResponse that = (SearchAggregateResponse) o;
      return category.equals(that.category) && labels.equals(that.labels)
          && datasets.equals(that.datasets);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, labels, datasets);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("category", category).add("labels", labels)
        .add("datasets", datasets).toString();
  }

  public static Builder newSearchAggregateResponse() {
    return new Builder();
  }

  public static class PartyData {
    private final String party;
    private final ImmutableList<Integer> data;

    PartyData(String party, ImmutableList<Integer> data) {
      this.party = party;
      this.data = data;
    }

    public String getParty() {
      return party;
    }

    public ImmutableList<Integer> getData() {
      return data;
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof PartyData) {
        PartyData that = (PartyData) o;
        return party.equals(that.party) && data.equals(that.data);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Objects.hash(party, data);
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this).add("party", party).add("data", data).toString();
    }
  }
}
