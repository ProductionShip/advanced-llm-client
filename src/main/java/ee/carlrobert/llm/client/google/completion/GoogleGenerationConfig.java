package ee.carlrobert.llm.client.google.completion;

import java.util.List;

/**
 * <a href="https://ai.google.dev/api/rest/v1/GenerationConfig?authuser=1">Gemini API
 * GenerationConfig</a>.
 */
public class GoogleGenerationConfig {

  private final List<String> stopSequences;
  private final int candidateCount;
  private final double temperature;
  private final int maxOutputTokens;
  private final double topP;
  private final int topK;

  public GoogleGenerationConfig(Builder builder) {
    this.stopSeque