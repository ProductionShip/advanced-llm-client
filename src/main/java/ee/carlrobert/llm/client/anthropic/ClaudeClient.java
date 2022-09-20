package ee.carlrobert.llm.client.anthropic;

import static ee.carlrobert.llm.client.DeserializationUtil.OBJECT_MAPPER;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.carlrobert.llm.PropertiesLoader;
import ee.carlrobert.llm.client.DeserializationUtil;
import ee.carlrobert.llm.client.anthropic.completion.ClaudeCompletionErrorDetails;
import ee.carlrobert.llm.client.anthropic.completion.ClaudeCompletionRequest;
import ee.carlrobert.llm.client.anthropic.completion.ClaudeCompletionResponse;
import ee.carlrobert.llm.client.anthropic.completion.ClaudeCompletionStreamResponse;
import ee.carlrobert.llm.client.openai.completion.ErrorDetails;
import ee.carlrobert.llm.completion.CompletionEventListener;
import ee.carlrobert.llm.completion.CompletionEventSourceListener;
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

public class ClaudeClient {

  private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");

  private final OkHttpClient httpClient;
  private final String apiKey;
  private final String apiVersion;
  private final String host;

  @Deprecated
  public ClaudeClient(String apiKey, String apiVersion, OkHttpClient.Builder httpClientBuilder) {
    this.httpClient = httpClientBuilder.build();
    this.apiKey = apiKey;
    this.apiVersion = apiVersion;
    this.host = PropertiesLoader.getValue("anthropic.baseUrl");
  }

  public Claud