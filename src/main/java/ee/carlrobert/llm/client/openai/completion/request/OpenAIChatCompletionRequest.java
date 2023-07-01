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
  private final double presencePenalty;
  private final boolean stream;
  @JsonIgnore
  private final String overriddenPath;
  private final List<Tool> tools;
  @JsonProperty("tool_choice")
  private final String toolChoice;
  @JsonProperty("response_format")
  private final ResponseFormat responseFormat;

  private OpenAIChatCompletionRequest(Builder builder) {
    this.model = builder.model;
    this.messages = builder.messages;
    this.maxTokens = builder.maxTokens;
    this.temperature = builder.temperature;
    this.frequencyPenalty = builder.frequencyPenalty;
    this.presencePenalty = builder.presencePenalty;
    this.stream = builder.stream;
    this.overriddenPath = builder.overriddenPath;
    this.tools = builder.tools;
    this.toolChoice = builder.toolChoice;
    this.responseFormat = builder.responseFormat;
  }

  public void addMessage(OpenAIChatCompletionMessage message) {
    messages.add(message);
  }

  public List<OpenAIChatCompletionMessage> getMessages() {
    return messages;
  }

  public String getModel() {
    return model;
  }

  public int getMaxTokens() {
    return maxT