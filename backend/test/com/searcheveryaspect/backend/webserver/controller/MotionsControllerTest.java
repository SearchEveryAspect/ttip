package com.searcheveryaspect.backend.webserver.controller;

import static org.junit.Assert.*;

import com.searcheveryaspect.backend.ESQuerier;
import com.searcheveryaspect.backend.ESRequest;
import com.searcheveryaspect.backend.webserver.SearchAggregateResponse;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for MotionsController.
 */
public class MotionsControllerTest {
  final String to = "2015-03-01";
  final String from = "2015-01-01";
  final String period = "month";
  final String category = "skatt";

  @Mock
  ESQuerier esqMock;
  @Mock
  Response resMock;
  @Mock
  Request reqMock;
  @Mock
  SearchAggregateResponse sarMock;

  @Before
  /**
   * 
   */
  public void create() {
    MockitoAnnotations.initMocks(this);
    when(reqMock.getHeader("debug")).thenReturn(null);
    when(reqMock.getHeader("from_date")).thenReturn(from);
    when(reqMock.getHeader("to_date")).thenReturn(to);
    when(reqMock.getHeader("category")).thenReturn(category);
    when(reqMock.getHeader("interval")).thenReturn(period);
  }

  @Test
  public void testValidQuery() {
    // ESRequest for parameter matching.
    List<String> cats = new ArrayList<>();
    cats.add(category);
    ESRequest esreq =
        new ESRequest(new Interval(DateTime.parse(from, DateTimeFormat.forPattern("yyyy-mm-dd")),
            DateTime.parse(to, DateTimeFormat.forPattern("yyyy-mm-dd"))), cats, period);

    when(esqMock.fetchDocuments(esreq)).thenReturn(sarMock);

    // Controller to test behaviour on.
    MotionsController t = new MotionsController(esqMock);

    assertEquals(sarMock, t.read(reqMock, resMock));
    verify(esqMock).fetchDocuments(esreq);
  }

  @Test
  public void testNoFromDateQuery() {
    // TODO
  }

  @Test
  public void testNoNoDateQuery() {
    // TODO
  }

  @Test
  public void testNoCategoryDateQuery() {
    // TODO
  }

  @Test
  public void testNoIntervalDateQuery() {
    // TODO
  }

  @Test
  public void testWrongFromDateQuery() {
    // TODO
  }

  @Test
  public void testWrongToDateQuery() {
    // TODO
  }

  @Test
  public void testWrongCategoryDateQuery() {
    // TODO
  }

  @Test
  public void testWrongIntervalDateQuery() {
    // TODO
  }

  @Test
  public void testFromAfterBeforeQuery() {
    // TODO
  }
}
