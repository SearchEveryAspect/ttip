package com.searcheveryaspect.backend.webserver;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/**
 * 
 */
@AutoValue
abstract class SearchAggregateResponse {
  SearchAggregateResponse() {}

  static Builder builder() {
    return new AutoValue_SearchAggregateResponse.Builder();
  }


  abstract String category();

  abstract ImmutableList<String> labels();

  abstract ImmutableList<PartyData> datasets();

  @AutoValue
  abstract static class PartyData {
    PartyData() {}

    static Builder builder() {
      return new AutoValue_SearchAggregateResponse_PartyData.Builder();
    }

    abstract String party();

    abstract ImmutableList<Integer> data();

    @AutoValue.Builder
    interface Builder {
      Builder party(String s);

      Builder data(ImmutableList<String> d);

      PartyData build();
    }
  }

  @AutoValue.Builder
  interface Builder {
    Builder category(String s);

    Builder labels(ImmutableList<String> l);

    Builder datasets(ImmutableList<PartyData> d);

    SearchAggregateResponse build();
  }


  // public static SearchAggregateResponse builder() {
  // return new AutoValue_SearchAggregateResponse.Builder().build();
  // }
}
