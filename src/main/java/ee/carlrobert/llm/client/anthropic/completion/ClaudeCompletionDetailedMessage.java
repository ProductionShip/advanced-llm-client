package ee.carlrobert.llm.client.anthropic.completion;

import java.util.Collections;
import java.util.List;

public class ClaudeCompletionDetailedMessage implements ClaudeCompletionMessage {

  private String role;
  private List<ClaudeMessageContent> content;
