package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.Objects;


/**
 * SearchAggregateResponse represent the result of a search for documents within a
 * specific period of time.
 */
public class SearchResponse {
  private final String category;
  private final ImmutableList<String> labels;
  private final ImmutableList<Party> datasets;

  SearchResponse(Builder builder) {
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

  public ImmutableList<Party> getDatasets() {
    return datasets;
  }

  public static class Builder {
    private String category;
    private ImmutableList<String> labels;
    private ImmutableList<Party> datasets;

    public Builder category(String s) {
      category = s;
      return this;
    }

    public Builder labels(ImmutableList<String> l) {
      labels = l;
      return this;
    }

    public Builder datasets(ImmutableList<Party> l) {
      datasets = l;
      return this;
    }

    public SearchResponse build() {
      return new SearchResponse(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SearchResponse) {
      SearchResponse that = (SearchResponse) o;
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

  public static Builder newSearchResponse() {
    return new Builder();
  }

  public static class Party {
    private final String party;
    private final ImmutableList<Entry> data;

    public Party(String party, ImmutableList<Entry> data) {
      this.party = party;
      this.data = data;
    }

    public String getParty() {
      return party;
    }

    public ImmutableList<Entry> getData() {
      return data;
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Party) {
        Party that = (Party) o;
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

    public static class Entry {
      private final int data;
      private final ImmutableList<Document> docs;

      public Entry(int data, ImmutableList<Document> docs) {
        this.data = data;
        this.docs = docs;
      }

      public int getData() {
        return data;
      }

      public ImmutableList<Document> getDocs() {
        return docs;
      }

      @Override
      public boolean equals(Object o) {
        if (o instanceof Entry) {
          Entry that = (Entry) o;
          return data == that.data && docs.equals(that.docs);
        }
        return false;
      }

      @Override
      public int hashCode() {
        return Objects.hash(data, docs);
      }

      @Override
      public String toString() {
        return MoreObjects.toStringHelper(this).add("data", data).add("docs", docs).toString();
      }


      public static class Document {
        private final String title;
        private final String link;
        private final String date;

        public Document(Builder b) {
          this.title = b.title;
          this.link = b.link;
          this.date = b.date;
        }

        public String getName() {
          return title;
        }

        public String getLink() {
          return link;
        }

        public String getDate() {
          return date;
        }

        @Override
        public boolean equals(Object o) {
          if (o instanceof Document) {
            Document that = (Document) o;
            return title.equals(that.title) && link.equals(that.link) && date.equals(that.date);
          }
          return false;
        }

        @Override
        public int hashCode() {
          return Objects.hash(title, link, date);
        }

        @Override
        public String toString() {
          return MoreObjects.toStringHelper(this).add("title", title).add("link", link)
              .add("date", date).toString();
        }

        public static class Builder {
          private String title;
          private String link;
          private String date;

          public Builder title(String s) {
            this.title = s;
            return this;
          }

          public Builder link(String s) {
            this.link = s;
            return this;
          }

          public Builder date(String s) {
            this.date = s;
            return this;
          }

          public Document build() {
            return new Document(this);
          }
        }

        public static Builder newDocument() {
          return new Builder();
        }
      }
    }
  }
}
