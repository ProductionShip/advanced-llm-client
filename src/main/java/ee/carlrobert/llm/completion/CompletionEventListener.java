package ee.carlrobert.llm.completion;

import ee.carlrobert.llm.client.openai.completion.ErrorDetails;
import okhttp3.sse.EventSource;

public interface CompletionEventListener<T> {

  default void onMessage(T message, String rawMessage, EventSo