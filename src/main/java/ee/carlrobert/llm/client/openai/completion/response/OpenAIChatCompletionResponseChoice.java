package ee.carlrobert.llm.client.openai.completion.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAIChatCompletionResponseChoice {

  private final OpenAIChatCompletionResponseChoiceDelta delta;
  private final OpenAIChatCompletionResponseChoiceDelta message;
  private final String finishReason;

  @JsonCreator(mode = JsonCreator.Mode.PROPER