package ee.carlrobert.llm.client.openai.completion;

import static ee.carlrobert.llm.client.DeserializationUtil.OBJECT_MAPPER;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.carlrobert.llm.client.openai.completion.response.OpenAIChatCompletionResponse;
import ee.carlrobert.llm.client.openai.completion.response.OpenAIChatCompletionResponseChoice;
import ee.carlrobert.llm.client.openai.completion.response.OpenAIChatCompletionResponseChoiceDelta;
import ee.carlrobert.llm.completion.CompletionEventListener;
import ee.carlrobert.llm.completion.CompletionEventSourceListener;
import java.util.Objects;
import java.util.stream.Stream;

public class OpenAIChatCompletionEventSourceListener extends CompletionEventSourceListener<String> {

  public OpenAIChatCompletionEventSourceListener(CompletionEventListener<String> listener) {
    super(listener);
  }

  /**
   * Content of the first choice.
   * <ul>
   *     <li>Search all choices which are not null</li>
   *     <li>Search all deltas which are not null</li>
   *     <li>Use first content which is not null or blank (whitespace)</li>
   *     <li>