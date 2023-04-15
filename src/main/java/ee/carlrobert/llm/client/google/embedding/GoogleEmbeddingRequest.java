package ee.carlrobert.llm.client.google.embedding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionContent;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class GoogleEmbeddingRequest {

  private final GoogleCompletionContent content;
  private final TaskType taskType;
  private 