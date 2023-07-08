package ee.carlrobert.llm.client.openai.completion.request;

public class OpenAIChatCompletionStandardMessage implements OpenAIChatCompletionMessage {

  private final String role;
  private final String