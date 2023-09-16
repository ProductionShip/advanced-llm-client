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
      CompletionEventListener completionEventListener) {
    return EventSources.createFactory(httpClient)
        .newEventSource(buildHttpRequest(request), getEventSourceListener(completionEventListener));
  }

  private Request buildHttpRequest(YouCompletionRequest request) {
    var guestIdCookie = request.getUserId() != null
        ? ("uuid_guest=" + request.getUserId().toString() + "; ")
        : "";
    return new Request.Builder()
        .url(buildHttpUrl(request))
        .header("Accept", "text/event-stream")
        .header("Cache-Control", "no-cache")
        .header("User-Agent", "youide CodeGPT")
        .header("Cookie", (
            guestIdCookie
                + "safesearch_guest=Moderate; "
                + "youpro_subscription=true; "
                + "you_subscription=free; "
                + "stytch_session=" + sessionId + "; "
                + "ydc_stytch_session=" + sessionId + "; "
                + "stytch_session_jwt=" + accessToken + "; "
                + "ydc_stytch_session_jwt=" + accessToken + "; "
                + "eg4=" + request.isUseGPT4Model() + "; "
                + "__cf_bm=aN2b3pQMH8XADeMB7bg9s1bJ_bfXBcCHophfOGRg6g0-1693601599-0-AWIt5Mr4Y3xQI4m"
                + "IJ1lSf4+vijWKDobrty8OopDeBxY+NABe0MRFidF3dCUoWjRt8SVMvBZPI3zkOgcRs7Mz3yazd7f7c58"
                + "HwW5Xg9jdBjNg;"))
        .get()
        .build();
  }

  private HttpUrl buildHttpUrl(YouCompletionRequest request) {
    try {
      var url = new URL(BASE_HOST);
      var httpUrlBuilder = new HttpUrl.Builder()
          .scheme(url.getProtocol())
          .host(url.getHost())
          .addPathSegments("api/streamingSearch")
          .addQueryParameter("q", request.getPrompt())
          .addQueryParameter("page", "1")
          .addQueryParameter("cfr", "CodeGPT")
          .addQueryParameter("count", "10")
          .addQueryParameter(
              "safeSearch",
              "WebPages,Translations,TimeZone,Computation,RelatedSearches")
          .addQueryParameter("domain", "youchat")
          .addQueryParameter("selectedChatMode", request.getChatMode().toString())
          .addQueryParameter("chat", OBJECT_MAPPER.writeValueAsString(request.getMessages()));

      if (request.getChatMode().isSupportCustomModel()) {
        httpUrlBuilder.addQueryParameter("selectedAIModel", request.getCustomModel().toString());
      }

      if (url.getPort() != -1) {
   