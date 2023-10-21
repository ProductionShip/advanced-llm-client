package ee.carlrobert.llm.client.you.completion;

import java.util.Arrays;

public enum YouCompletionMode {
  DEFAULT("default", false),
  AGENT("agent", false),
  CUSTOM("custom", true),
  RESEARCH("research", false);

  private final String code;
  private final boolean supportCust