package ee.carlrobert.llm.client.openai.completion.request;

import java.util.Collections;
import java.util.List;

public class OpenAIChatCompletionDetailedMessage implements OpenAIChatCompletionMessage {

  private final String role;
  private List<OpenAIMessageContent> content;

  public OpenAIChatCompletionDetailedMessage(String role, OpenAIMessageContent content) {
    this.role = role;
    this.content = Collections.s