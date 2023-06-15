package ee.carlrobert.llm.client.openai.completion;

import static ee.carlrobert.llm.client.DeserializationUtil.OBJECT_MAPPER;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.carlrobert.llm.client.openai.completion.response.OpenAITextCompletionResponse;
import ee.carlrobert.llm.client.openai.completion.response.OpenAITextCompletionResponseChoice;
import ee.carlrobert.llm.completion.CompletionEventListener;
import ee.carlrobert.llm.completion.CompletionEventSourceListener;
import java.util.Objects;
import java.util.stream.Stream;

public class OpenAITextCompletionEventSourceListener extends CompletionEventSourceListener {

  public OpenAITextCompletionEventSourceListener(CompletionEventListener listeners) {
    super(listeners);
  }

  /**
   * Text of the first choice.
   * <ul>
   *     <li>Search all choices which are not null</li>
   *     <li>Use first text which is not null or blank (whitespace)</li>
   *     <li>Otherwise use "" (empty string) if no match can be found</li>
   * </ul>
   *
   * @return First non-blank content which can be found, otherwise {@code ""