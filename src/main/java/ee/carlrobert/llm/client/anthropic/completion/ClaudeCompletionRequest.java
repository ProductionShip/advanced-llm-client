package ee.carlrobert.llm.client.anthropic.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.carlrobert.llm.completion.CompletionRequest;
import java.util.List;

public class ClaudeCompletionRequest implements CompletionRequest {

  private String model;
  private String system;
  private List<ClaudeCompletionMessage> messages;
  @JsonProperty("max_tokens")
  private int maxTokens;
  private boolean stream;
  private double temperature;
  @JsonProperty("top_k")
  private int topK;
  @JsonProperty("top_p")
  private int topP;

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSystem() {
    return system;
  }

  public void setSystem(String system) {
    this.system = system;
  }

  public List<ClaudeCompletionMessage> getMessages() {
    return messages;
  }

  public