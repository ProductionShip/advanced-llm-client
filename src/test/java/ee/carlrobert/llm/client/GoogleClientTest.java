
package ee.carlrobert.llm.client;

import static ee.carlrobert.llm.client.util.JSONUtil.e;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonArray;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonMap;
import static ee.carlrobert.llm.client.util.JSONUtil.jsonMapResponse;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.carlrobert.llm.client.google.GoogleClient;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionContent;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionRequest;
import ee.carlrobert.llm.client.google.completion.GoogleGenerationConfig;
import ee.carlrobert.llm.client.google.embedding.GoogleEmbeddingContentRequest;
import ee.carlrobert.llm.client.google.models.GoogleModel;
import ee.carlrobert.llm.client.http.ResponseEntity;
import ee.carlrobert.llm.client.http.exchange.BasicHttpExchange;
import ee.carlrobert.llm.client.http.exchange.StreamHttpExchange;
import ee.carlrobert.llm.client.openai.completion.ErrorDetails;
import ee.carlrobert.llm.completion.CompletionEventListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import okhttp3.sse.EventSource;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GoogleClientTest extends BaseTest {

  @Test
  void shouldStreamChatCompletion() {
    var prompt = "TEST_PROMPT";
    var resultMessageBuilder = new StringBuilder();
    expectGoogle((StreamHttpExchange) request -> {
      assertThat(request.getUri().getPath()).isEqualTo(
          "/v1/models/gemini-1.0-pro:streamGenerateContent");
      assertThat(request.getUri().getQuery()).isEqualTo("key=TEST_API_KEY&alt=sse");
      assertThat(request.getMethod()).isEqualTo("POST");
      assertThat(request.getBody())
          .extracting(
              "generationConfig.temperature",
              "generationConfig.maxOutputTokens",
              "contents"
          )
          .containsExactly(
              0.5,
              500,
              List.of(Map.of("parts", List.of(Map.of("text", prompt)), "role", "user"))
          );
      return List.of(
          jsonMapResponse("candidates",
              jsonArray(jsonMap("content", jsonMap("parts", jsonArray(jsonMap("text", "Hello")))))),
          jsonMapResponse("candidates",
              jsonArray(jsonMap("content", jsonMap("parts", jsonArray(jsonMap("text", "!")))))));
    });

    new GoogleClient.Builder("TEST_API_KEY")
        .build()
        .getChatCompletionAsync(
            new GoogleCompletionRequest.Builder(
                List.of(new GoogleCompletionContent("user", List.of(prompt))))
                .generationConfig(new GoogleGenerationConfig.Builder()
                    .temperature(0.5)
                    .maxOutputTokens(500)
                    .build())
                .build(),
            GoogleModel.GEMINI_1_0_PRO,
            new CompletionEventListener<String>() {
              @Override
              public void onMessage(String message, EventSource eventSource) {
                resultMessageBuilder.append(message);
              }
            });

    await().atMost(5, SECONDS).until(() -> "Hello!".contentEquals(resultMessageBuilder));
  }


  @Test
  void shouldGetChatCompletion() {
    var prompt = "TEST_PROMPT";
    expectGoogle((BasicHttpExchange) request -> {
      assertThat(request.getUri().getPath()).isEqualTo(
          "/v1/models/gemini-1.0-pro:generateContent");
      assertThat(request.getUri().getQuery()).isEqualTo("key=TEST_API_KEY");
      assertThat(request.getMethod()).isEqualTo("POST");
      assertThat(request.getBody())
          .extracting(
              "generationConfig.temperature",
              "generationConfig.maxOutputTokens",
              "contents"
          )
          .containsExactly(
              0.5,
              500,
              List.of(Map.of("parts", List.of(Map.of("text", prompt)), "role", "user"))
          );
      return new ResponseEntity(new ObjectMapper().writeValueAsString(Map.of("candidates", List.of(
          Map.of("content", Map.of(
              "role", "assistant",
              "parts", List.of(Map.of("text", "This is a test"))))))));
    });

    var response = new GoogleClient.Builder("TEST_API_KEY")
        .build()
        .getChatCompletion(new GoogleCompletionRequest.Builder(