package com.searcheveryaspect.backend.shared;

import com.google.common.base.MoreObjects;

import org.joda.time.DateTime;

import java.util.Objects;

/**
 * ESUpdateInfo contains information about the last time the database was updated.
 */
public class ESUpdateInfo {
  // When the database was last updated. Format yy-mm-dd.
  final String ts;

  public ESUpdateInfo(String ts) {
    this.ts = ts;
  }

  public ESUpdateInfo(DateTime ts) {
    this.ts = ts.toString("yy-MM-dd");
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ESUpdateInfo) {
      ESUpdateInfo that = (ESUpdateInfo) o;
      return ts.equals(that.ts);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ts);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("ts", ts).toString();
  }
}
