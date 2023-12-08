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
  private final boolean useGPT4Model;

  private final YouCompletionMode chatMode;
  private final YouCompletionCustomModel customModel;

  public YouCompletionRequest(Builder builder) {
    this.prompt = builder.prompt;
    this.messages = builder.messages;
    this.chatId = builder.chatId;
    this.userId = builder.userId;
    this.queryTraceId = builder.queryTraceId;
    this.useGPT4Model = builder.useGPT4Model;
    this.chatMode = builder.chatMode;
    this.customModel = builder.customModel;
  }

  public Str