package ee.carlrobert.llm.client.anthropic.completion;

import java.util.Collections;
import java.util.List;

public class ClaudeCompletionDetailedMessage implements ClaudeCompletionMessage {

  private String role;
  private List<ClaudeMessageContent> content;

  public ClaudeCompletionDetailedMessage(String role, ClaudeMessageContent content) {
    this.role = role;
    this.content = Collections.singletonList(content);
  }

  public ClaudeCompletionDetailedMessage(String role, List<ClaudeMessageContent> content) {
    this.ro