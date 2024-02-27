package ee.carlrobert.llm.completion;

import static java.lang.String.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.carlrobert.llm.client.openai.completion.ErrorDetails;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.function.Consumer;
import okhttp3.Response;
import okhttp3.internal.http2.StreamResetException;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CompletionEventSourceListener<T> extends EventSourceListener {

  private static final Logger LOG = LoggerFactory.getLogger(CompletionEventSourceListener.class);

  private final CompletionEventListener<T> listeners;
  private final StringBuilder messageBuilder = new StringBuilder();
  private final boolean retryOnReadTimeout;
  private final Consumer<String> onRetry;

  public CompletionEventSourceListener(CompletionEventListener<T> listeners) {
    this(listeners, false, null);
  }

  public CompletionEventSourceListener(CompletionEventListener<T> listeners,
      boolean retryOnReadTimeout, Consumer<String> onRetry) {
    this.listeners = listeners;
    this.retryOnReadTimeout = retryOnReadTimeout;
    this.onRetry = onRetry;
  }

  protected abstract T getMessage(String data) throws JsonProcessingException;

  protected abstract ErrorDetails getErrorDetails(String data) throws JsonProcessingException;

  public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
    LOG.info("Request opened.");
  }

  public void onClosed(@NotNull EventSource eventSource) {
    LOG.info("Request closed.");
    listeners.onComplete(messageBuilder);
  }

  public void onEvent(
      @NotNull EventSource eventSource,
      String id,
      String type,
      @NotNull String data) {
    try {
      // Redundant end signal so just ignore
      if ("[DONE]".equals(data)) {
        return;
      }

      var message = getMessage(data);
      if (message != null) {
        messageBuilder.append(message);
        listeners.onMessage(message, eventSource);
        listeners.onMessage(message, data, eventSource);
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable to deserialize payload.", e);
    }
  }

  public void onFailure(
      @NotNull EventSource eventSource,
      Throwable throwable,
      Response response) {
    if (throwable instanceof StreamResetException
        || (throwable instanceof SocketException
        && "Socket c