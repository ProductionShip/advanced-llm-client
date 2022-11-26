
package ee.carlrobert.llm.client.azure;

import static ee.carlrobert.llm.client.DeserializationUtil.OBJECT_MAPPER;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import ee.carlrobert.llm.PropertiesLoader;
import ee.carlrobert.llm.client.DeserializationUtil;
import ee.carlrobert.llm.client.openai.completion.ErrorDetails;
import ee.carlrobert.llm.client.openai.completion.OpenAIChatCompletionEventSourceListener;
import ee.carlrobert.llm.client.openai.completion.request.OpenAIChatCompletionRequest;
import ee.carlrobert.llm.client.openai.completion.response.OpenAIChatCompletionResponse;
import ee.carlrobert.llm.completion.CompletionEventListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;

public class AzureClient {

  private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");
  private static final String BASE_URL = PropertiesLoader.getValue("azure.openai.baseUrl");

  private final OkHttpClient httpClient;
  private final String apiKey;
  private final AzureCompletionRequestParams requestParams;
  private final boolean activeDirectoryAuthentication;
  private final String url;

  private AzureClient(Builder builder, OkHttpClient.Builder httpClientBuilder) {
    this.httpClient = httpClientBuilder.build();
    this.apiKey = builder.apiKey;
    this.requestParams = builder.requestParams;
    this.activeDirectoryAuthentication = builder.activeDirectoryAuthentication;
    this.url = String.format(BASE_URL, requestParams.getResourceName());
  }

  public EventSource getChatCompletionAsync(
      OpenAIChatCompletionRequest request,
      CompletionEventListener<String> completionEventListener) {
    return EventSources.createFactory(httpClient)
        .newEventSource(buildHttpRequest(request), getEventSourceListener(completionEventListener));
  }

  public OpenAIChatCompletionResponse getChatCompletion(OpenAIChatCompletionRequest request) {
    try (var response = httpClient.newCall(buildHttpRequest(request)).execute()) {
      return DeserializationUtil.mapResponse(response, OpenAIChatCompletionResponse.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Request buildHttpRequest(OpenAIChatCompletionRequest completionRequest) {