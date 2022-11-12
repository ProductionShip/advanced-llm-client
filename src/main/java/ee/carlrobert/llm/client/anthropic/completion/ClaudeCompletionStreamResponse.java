package ee.carlrobert.llm.client.anthropic.completion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.carlrobert.llm.completion.CompletionResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClaudeCompletionStreamResponse implements CompletionResponse {

  private String type;
  private int index;
  private ClaudeCompletionResponseMessage delta;