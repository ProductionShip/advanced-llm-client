package ee.carlrobert.llm.client.google.embedding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionContent;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class GoogleEmbeddingRequest {

  private final GoogleCompletionContent content;
  private final TaskType taskType;
  private final String title;
  private final Integer outputDimensionality;

  public GoogleEmbeddingRequest(Builder builder) {
    this.content = builder.content;
    this.taskType = builder.taskType;
    this.title = builder.title;
    this.outputDimensionality = builder.outputDimensionality;
  }

  public GoogleCompletionContent getContent() {
    return content;
  }

  public TaskType getTaskType() {
    return taskType;
  }

  public String getTitle() {
    return title;
  }

  public Integer getOutputDimensionality() {
    return outputDimension