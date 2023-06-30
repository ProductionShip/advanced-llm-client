package ee.carlrobert.llm.client.openai.completion.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ee.carlrobert.llm.client.openai.completion.OpenAIChatCompletionModel;
import ee.carlrobert.llm.completion.CompletionRequest;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenAIChatCompletionRequest implements CompletionRequest {

  private final String model;
  private final List<OpenAIChatCompletionMessage> messages;
  @JsonProperty("max_tokens")
  private final int maxTokens;
  private final double temperature;
  @JsonProperty("frequency_penalty")
  private final double frequencyPenalty;
  @JsonProperty("presence_penalty")
  private final double presencePena