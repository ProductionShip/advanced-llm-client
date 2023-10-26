package ee.carlrobert.llm.client.you.completion;

import ee.carlrobert.llm.completion.CompletionRequest;
import java.util.List;
import java.util.UUID;

public class YouCompletionRequest implements CompletionRequest {

  private final String prompt;
  private final List<YouCompletionRequestMessage> messages;
  private final UUID chatId;
  private final UUID userId;
  private final UUID queryTraceId;
  pr