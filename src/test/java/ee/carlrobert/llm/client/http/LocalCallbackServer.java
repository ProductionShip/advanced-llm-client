package ee.carlrobert.llm.client.http;

import static java.lang.String.format;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ee.carlrobert.llm.client.http.expectation.BasicExpectation;
import ee.carlrobert.llm.client.http.expectation.Expecta