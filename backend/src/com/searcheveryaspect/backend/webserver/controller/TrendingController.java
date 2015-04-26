package com.searcheveryaspect.backend.webserver.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.DatabaseReader;
import com.searcheveryaspect.backend.TrendingRequest;
import com.searcheveryaspect.backend.webserver.TrendingResponse;

import org.restexpress.Request;
import org.restexpress.Response;

/**
 * 
 */
public class TrendingController extends ReadOnlyController {
  private final DatabaseReader<TrendingRequest, TrendingResponse> reader;

  public TrendingController(DatabaseReader<TrendingRequest, TrendingResponse> reader) {
    this.reader = checkNotNull(reader);
  }

  public TrendingResponse read(Request request, Response response) {
    return null;
  }
}
