package ee.carlrobert.llm.client.you;

import static ee.carlrobert.llm.client.DeserializationUtil.OBJECT_MAPPER;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import ee.carlrobert.llm.PropertiesLoader;
import ee.carlrobert.llm.client.openai.completion.ErrorDetails;
import ee.carlrobert.llm.client.you.completion.YouCompletionEventListener;
import ee.carlrobert.llm.client.you.completion.YouCompletionRequest;
import ee.carlrobert.llm.client.you.completion.YouCompletionResponse;
import ee.carlrobert.llm.completion.CompletionEventListener;
import ee.carlrobert.llm.completion.CompletionEventSourceListener;
import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;

public class YouClient {

  private static final String BASE_HOST = PropertiesLoader.getValue("you.baseUrl");

  private final OkHttpClient httpClient;
  private final String sessionId;
  private final String accessToken;
  private final UTMParameters utmParameters;

  private YouClient(YouClient.Builder builder) {
    this.httpClient = new OkHttpClient.Builder().build();
    this.sessionId = builder.sessionId;
    this.accessToken = builder.accessToken;
    this.utmParameters = builder.utmParameters;
  }

  public EventSource getChatCompletionAsync(
      YouCompletionRequest request,
      CompletionEv