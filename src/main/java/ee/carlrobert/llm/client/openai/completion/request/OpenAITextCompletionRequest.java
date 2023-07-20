
package ee.carlrobert.llm.client.openai.completion.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.carlrobert.llm.completion.CompletionRequest;

public class OpenAITextCompletionRequest implements CompletionRequest {

  private final String model;
  private final String prompt;
  private final String suffix;
  @JsonProperty("max_tokens")
  private final int maxTokens;
  private final double temperature;
  @JsonProperty("frequency_penalty")
  private final double frequencyPenalty;
  @JsonProperty("presence_penalty")
  private final double presencePenalty;
  private final boolean stream;

  protected OpenAITextCompletionRequest(Builder builder) {
    this.model = builder.model;
    this.prompt = builder.prompt;
    this.suffix = builder.suffix;
    this.maxTokens = builder.maxTokens;
    this.temperature = builder.temperature;
    this.frequencyPenalty = builder.frequencyPenalty;
    this.presencePenalty = builder.presencePenalty;
    this.stream = builder.stream;
  }

  public int getMaxTokens() {
    return maxTokens;
  }

  public String getModel() {
    return model;
  }

  public String getPrompt() {
    return prompt;