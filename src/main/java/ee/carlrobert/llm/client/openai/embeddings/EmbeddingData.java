package ee.carlrobert.llm.client.openai.embeddings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingData {

  private double[] embedding;

  public double[] getEmbedding() {
    retur