
package ee.carlrobert.llm.client;

import static ee.carlrobert.llm.client.util.JSONUtil.e;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonArray;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonMap;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonMapResponse;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import ee.carlrobert.llm.client.anthropic.ClaudeClient;
import ee.carlrobert.llm.client.anthropic.completion.ClaudeCompletionRequest;
import ee.carlrobert.llm.client.anthropic.completion.ClaudeCompletionStandardMessage;
import ee.carlrobert.llm.client.http.ResponseEntity;
import ee.carlrobert.llm.client.http.exchange.BasicHttpExchange;
import ee.carlrobert.llm.client.http.exchange.StreamHttpExchange;
import ee.carlrobert.llm.completion.CompletionEventListener;
import java.util.List;
import java.util.Map;
import okhttp3.sse.EventSource;
import org.junit.jupiter.api.Test;

public class ClaudeClientTest extends BaseTest {

  @Test
  public void shouldStreamCompletion() {
    var request = new ClaudeCompletionRequest();
    request.setModel("claude-3");
    request.setStream(true);
    request.setMaxTokens(500);
    request.setMessages(List.of(new ClaudeCompletionStandardMessage("user", "USER_PROMPT")));
    expectAnthropic((StreamHttpExchange) exchange -> {
      assertThat(exchange.getUri().getPath()).isEqualTo("/v1/messages");
      assertThat(exchange.getMethod()).isEqualTo("POST");
      assertThat(exchange.getHeaders().get("Accept").get(0)).isEqualTo("text/event-stream");
      assertThat(exchange.getHeaders().get("X-api-key").get(0)).isEqualTo("TEST_API_KEY");
      assertThat(exchange.getHeaders().get("Anthropic-version").get(0))
          .isEqualTo("2000-01-01");
      assertThat(exchange.getBody())
          .extracting(
              "model",
              "stream",
              "max_tokens",
              "messages")
          .containsExactly(
              "claude-3",
              true,
              500,
              List.of(Map.of("role", "user", "content", "USER_PROMPT")));
      return List.of(
          jsonMapResponse("delta", jsonMap("text", "He")),
          jsonMapResponse("delta", jsonMap("text", "llo")),
          jsonMapResponse("delta", jsonMap("text", "!")));
    });

    var resultMessageBuilder = new StringBuilder();
    new ClaudeClient.Builder("TEST_API_KEY", "2000-01-01")
        .build()
        .getCompletionAsync(
            request,
            new CompletionEventListener<>() {
              @Override
              public void onMessage(String message, EventSource eventSource) {
                resultMessageBuilder.append(message);
              }
            });

    await().atMost(5, SECONDS).until(() -> "Hello!".contentEquals(resultMessageBuilder));
  }

  @Test
  public void shouldGetCompletion() {
    var request = new ClaudeCompletionRequest();