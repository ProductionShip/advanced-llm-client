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
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalCallbackServer {

  private static final Logger LOG = LoggerFactory.getLogger(LocalCallbackServer.class);

  private final AtomicInteger currentExpectationIndex = new AtomicInteger();
  private final List<Expectation> expectations = new CopyOnWriteArrayList<>();
  private final HttpServer server;

  public LocalCallbackServer(Service service) {
    try {
      server = HttpServer.create(new InetSocketAddress(0), 0);
    } catch (IOException e) {
      throw new RuntimeException("Could not create HttpServer", e);
    }
    server.setExecutor(null);
    server.createContext("/", exchange -> {
      try {
        var expectation = expectations.get(currentExpectationIndex.getAndIncrement());
        if (!expectation.getService().getUrlProperty().equals(service.getUrlProperty())) {
          try {
            throw new AssertionError(
                format("Expecting request \"%s\", but received \"%s\"",
                    s