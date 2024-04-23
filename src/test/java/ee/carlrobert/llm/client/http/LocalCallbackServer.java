package ee.carlrobert.llm.client.http;

import static java.lang.String.format;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ee.carlrobert.llm.client.http.expectation.BasicExpectation;
import ee.carlrobert.llm.client.http.expectation.Expectation;
import ee.carlrobert.llm.client.http.expectation.NdJsonStreamExpectation;
import ee.carlrobert.llm.client.http.expectation.StreamExpectation;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;