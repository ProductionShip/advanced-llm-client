package ee.carlrobert.llm.client.openai.completion.request;

public class ToolFunction {

  private String name;
  private String description;
  private ToolFunctionParameters parameters;

  public String getName() {
  