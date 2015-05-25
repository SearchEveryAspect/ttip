package com.searcheveryaspect.backend.webserver.controller;

import com.searcheveryaspect.backend.webserver.CategoryResponse;

import org.apache.log4j.Logger;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 * CategoryController provides a way to query for all the possible categories that can be
 * used to classify a motion.
 */
public class CategoryController extends ReadOnlyController {
  static final Logger logger = Logger.getLogger("webServerLogger.CategoryController");

  public CategoryResponse read(Request request, Response response) {
    if (logger.isInfoEnabled()) {
      logger.info("Received " + formatRequest(request));
    }
    return new CategoryResponse();
  }
}
