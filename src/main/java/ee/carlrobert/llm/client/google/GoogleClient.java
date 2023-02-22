package ee.carlrobert.llm.client.google;

import static ee.carlrobert.llm.client.DeserializationUtil.OBJECT_MAPPER;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import ee.carlrobert.llm.PropertiesLoader;
import ee.carlrobert.llm.client.DeserializationUtil;
import ee.carlrobert.llm.client.google.completion.ApiResponseError;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionContent;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionRequest;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionResponse;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionResponse.Candidate;
import ee.carlrobert.llm.client.google.completion.GoogleContentPart;
import ee.carlrobert.llm.client.google.embedding.ContentEmbedding;
import ee.carlrobert.llm.client.google.embedding.GoogleBatchEmbeddingResponse;
import ee.carlrobert.llm.client.google.embedding.GoogleEmbeddingContentRequest;
import ee.carlrobert.llm.client.google.embedding.GoogleEmbeddingRequest;
import ee.carlrobert.llm.client.google.embedding.GoogleEmbeddingResponse;
import ee.carlrobert.llm.client.google.models.GoogleModel;
import ee.carlrobert.llm.client.google.models.GoogleModelsResponse;
import ee.carlrobert.llm.client.google.models.GoogleModelsResponse.GeminiModelDetails;
import ee.carlrobert.llm.client.google.models.GoogleTokensResponse;
import ee.carlrobert.llm.client.openai.completion.ErrorDetails;
import ee.carlrobert.llm.completion.CompletionEventListener;
import ee.carlrobert.llm.completion.CompletionEventSourceListener;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;

public class GoogleClient {

  private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");

  private final OkHttpClient httpClient;
  private final String host;
  private final String apiKey;

  protected GoogleClient(Builder builder, OkHttpClient.Builder httpClientBuilder) {
    this.httpClient = httpClientBuilder.build();
    this.host = builder.host;
    this.apiKey = builder.apiKey;
  }

  public EventSource getChatCompletionAsync(
      GoogleCompletionRequest request,
      GoogleModel model,
      CompletionEventListener<String> eventListener) {
    return getChatCompletionAsync(request, model.getCode(), eventListener);
  }

  public EventSource getChatCompletionAsync(
      GoogleCompletionRequest request,
      String model,
      CompletionEventListener<String> eventListener) {
    return EventSources.createFactory(httpClient)
        .newEventSource(buildPostRequest(request, model, "streamGenerateContent", true),
            getEventSourceListener(eventListener));
  }

  /**
   * <a
   * href="https://ai.google.dev/api/rest/v1/models/generateContent?authuser=1">GenerateContent</a>.
   */
  public GoogleCompletionResponse getChatCompletion(GoogleCompletionRequest request,
      GoogleModel model) {
    return getChatCompletion(request, model.getCode());
  }

  /**
   * <a
   * href="https://ai.google.dev/api/rest/v1/models/generateContent?authuser=1">GenerateContent</a>.
   */
  public GoogleCompletionResponse getChatCompletion(GoogleCompletionRequest request, String model) {
    try (var response = httpClient.newCall(
        buildPostRequest(request, model, "generateContent", false)).execute()) {
      return DeserializationUtil.mapResponse(response, GoogleCompletionResponse.class);
    } catch (IOException e) {
      throw new RuntimeException(
          "Could not get llama completion for the given request:\n" + request, e);
    }
  }

  public double[] getEmbedding(String text, GoogleModel model) {
    return getEmbedding(List.of(text), model.getCode());
  }

  public double[] getEmbedding(String text, String model) {
    return getEmbedding(List.of(text), model);
  }

  public double[] getEmbedding(List<String> texts, GoogleModel model) {
    return getEmbedding(texts, model.getCode());
  }

  public double[] getEmbedding(List<String> texts, String model) {
    return getEmbedding(new GoogleEmbeddingRequest.Builder(new GoogleCompletionContent(texts))
        .build(), model);
  }

  /**
   * <a href="https://ai.google.dev/api/rest/v1/models/embedContent?authuser=1">EmbedContent</a>.
   */
  public double[] getEmbedding(GoogleEmbeddingRequest request, GoogleModel model) {
    return getEmbedding(request, model.getCode());
  }

  /**
   * <a href="https://ai.google.dev/api/rest/v1/models/embedContent?authuser=1">EmbedContent</a>.
   */
  public double[] getEmbedding(GoogleEmbeddingRequest request, String model) {
    try (var response = httpClient
        .newCall(buildPostRequest(request, model, "embedContent", false))
        .execute()) {

      return Optional.ofNullable(
              DeserializationUtil.mapResponse(response, GoogleEmbeddingResponse.class))
          .map(GoogleEmbeddingResponse::getEmbedding)
          .map(ContentEmbedding::getValues)
          .orElse(null);

    } catch (IOException e) {
      throw new RuntimeException("Unable to fetch embedding", e);
    }
  }

  /**
   * <a
   * href="https://ai.google.dev/api/rest/v1/models/batchEmbedContents?authuser=1">BatchEmbedContents</a>.
   */
  public List<double[]> getBatchEmbeddings(
      List<GoogleEmbeddingContentRequest> requests,
      GoogleModel model) {
    return getBatchEmbeddings(requests, model.getCode());
  }

  public List<double[]> getBatchEmbeddings(
      List<GoogleEmbeddingContentRequest> requests,
      String model) {
    try (var response = httpClient
        .newCall(buildPostRequest(Map.of("requests", requests), model, "batchEmbedContents", false))
        .execute()) {

      var embeddings = Optional.ofNullable(
              DeserializationUtil.mapResponse(response, GoogleBatchEmbeddingResponse.class))
          .map(GoogleBatchEmbeddingResponse::getEmbeddings)
          .stream()
          .flatMap(Collection::stream)
          .filter(Objects::nonNull)
          .map(ContentEmbedding::getValues)
          .filter(Objects::nonNull)
          .collect(toList());
      return embeddings.isEmpty() ? null : embeddings;

    } catch (IOException e) {
      throw new RuntimeException("Unable to fetch embedding", e);
    }
  }


  /**
   * <a href="https://ai.google.dev/api/rest/v1/models/list?authuser=1">Models List</a>.
   */
  public GoogleModelsResponse getModels(Integer pageSize, String pageToken) {
    String url = host + "/v1/models";
    HttpUrl.Builder urlBuilder 