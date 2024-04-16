
package ee.carlrobert.llm.client;

import static ee.carlrobert.llm.client.util.JSONUtil.e;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonArray;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonMap;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonMapResponse;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import ee.carlrobert.llm.client.http.ResponseEntity;
import ee.carlrobert.llm.client.http.exchange.BasicHttpExchange;
import ee.carlrobert.llm.client.http.exchange.NdJsonStreamHttpExchange;
import ee.carlrobert.llm.client.ollama.OllamaClient;
import ee.carlrobert.llm.client.ollama.completion.request.OllamaChatCompletionMessage;
import ee.carlrobert.llm.client.ollama.completion.request.OllamaChatCompletionRequest;
import ee.carlrobert.llm.client.ollama.completion.request.OllamaCompletionRequest;
import ee.carlrobert.llm.client.ollama.completion.request.OllamaCompletionRequest.Builder;
import ee.carlrobert.llm.client.ollama.completion.request.OllamaEmbeddingRequest;
import ee.carlrobert.llm.client.ollama.completion.request.OllamaParameters;
import ee.carlrobert.llm.client.ollama.completion.request.OllamaPullRequest;
import ee.carlrobert.llm.client.ollama.completion.response.OllamaModel;
import ee.carlrobert.llm.client.ollama.completion.response.OllamaPullResponse;
import ee.carlrobert.llm.completion.CompletionEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.sse.EventSource;
import org.junit.jupiter.api.Test;

public class OllamaClientTest extends BaseTest {

  private static final OllamaClient client = new OllamaClient.Builder().build();

  @Test
  void shouldStreamOllamaCompletion() {
    expectOllama((NdJsonStreamHttpExchange) request -> {
      assertThat(request.getUri().getPath()).isEqualTo("/api/generate");
      assertThat(request.getMethod()).isEqualTo("POST");
      assertThat(request.getBody())
          .extracting("model", "prompt", "options", "system", "stream")
          .containsExactly("codellama:7b", "TEST_PROMPT", Map.of("temperature", 0.8),
              "SYSTEM_PROMPT", true);
      assertThat(request.getHeaders())
          .flatExtracting("Accept", "Connection")
          .containsExactly("text/event-stream", "Keep-Alive");
      return List.of(
          jsonMapResponse(e("response", "Hel"), e("done", false)),
          jsonMapResponse(e("response", "lo"), e("done", false)),
          jsonMapResponse(e("response", "!"), e("done", true)));
    });

    var resultMessageBuilder = new StringBuilder();
    client.getCompletionAsync(
        new OllamaCompletionRequest.Builder("codellama:7b",
            "TEST_PROMPT")
            .setSystem("SYSTEM_PROMPT")
            .setStream(true)
            .setOptions(new OllamaParameters.Builder().temperature(0.8).build())
            .build(),
        new CompletionEventListener<>() {
          @Override
          public void onMessage(String message, EventSource eventSource) {
            resultMessageBuilder.append(message);
          }
        });

    await().atMost(5, SECONDS).until(() -> "Hello!".contentEquals(resultMessageBuilder));
  }

  @Test
  void shouldStreamOllamaChatCompletion() {
    expectOllama((NdJsonStreamHttpExchange) request -> {
      assertThat(request.getUri().getPath()).isEqualTo("/api/chat");
      assertThat(request.getMethod()).isEqualTo("POST");
      assertThat(request.getBody())
          .extracting("model", "messages", "options", "stream")
          .containsExactly("codellama:7b",
              List.of(Map.of("role", "user", "content", "TEST_PROMPT")),
              Map.of("temperature", 0.8),
              true);
      assertThat(request.getHeaders())
          .flatExtracting("Accept", "Connection")
          .containsExactly("text/event-stream", "Keep-Alive");
      return List.of(
          jsonMapResponse(
              e("model", "codellama:7b"),
              e("message", jsonMap(
                  e("role", "assistant"),
                  e("content", "Hel")
              )),
              e("done", "false")
          ),
          jsonMapResponse(
              e("model", "codellama:7b"),
              e("message", jsonMap(
                  e("role", "assistant"),
                  e("content", "lo")
              )),
              e("done", "false")
          ),
          jsonMapResponse(
              e("model", "codellama:7b"),
              e("message", jsonMap(
                  e("role", "assistant"),
                  e("content", "!")
              )),
              e("done", "true")
          ));
    });

    var resultMessageBuilder = new StringBuilder();
    client.getChatCompletionAsync(
        new OllamaChatCompletionRequest.Builder("codellama:7b",
            List.of(
                new OllamaChatCompletionMessage("user", "TEST_PROMPT", null)
            ))
            .setStream(true)
            .setOptions(new OllamaParameters.Builder().temperature(0.8).build())
            .build(),
        new CompletionEventListener<>() {
          @Override
          public void onMessage(String message, EventSource eventSource) {
            resultMessageBuilder.append(message);
          }
        });

    await().atMost(5, SECONDS).until(() -> "Hello!".contentEquals(resultMessageBuilder));
  }

  @Test
  void shouldGetOllamaCompletion() {
    expectOllama((BasicHttpExchange) request -> {
      assertThat(request.getUri().getPath()).isEqualTo("/api/generate");
      assertThat(request.getMethod()).isEqualTo("POST");