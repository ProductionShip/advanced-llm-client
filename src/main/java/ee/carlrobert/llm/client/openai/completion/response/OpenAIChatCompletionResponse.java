package ee.carlrobert.llm.client.openai.completion.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ee.carlrobert.llm.completion.CompletionResponse;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIChatCompletionResponse implements CompletionResponse {

  private final String id;
  private final List<OpenAIChatCompletionResponseChoice> choices;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public OpenAIChatCompletionResponse(
   