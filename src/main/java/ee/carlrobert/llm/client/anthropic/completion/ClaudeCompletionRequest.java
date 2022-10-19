package ee.carlrobert.llm.client.anthropic.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.carlrobert.llm.completion.CompletionRequest;
import java.util.List;

public class ClaudeCompletionRequest implements CompletionRequest {

  private String model;
  private String system;
  private List<ClaudeComplet