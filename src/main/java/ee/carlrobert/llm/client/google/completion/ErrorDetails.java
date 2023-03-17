package ee.carlrobert.llm.client.google.completion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ee.carlrobert.llm.client.BaseError;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetails extends BaseError {

  private final String message;
  private final String status;
  private final String code;

  public ErrorDetails(String message) {
    this(message, null, null);
  }

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public ErrorDetails(
      @Js