package ee.carlrobert.llm.client.openai.completion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ee.carlrobert.llm.client.BaseError;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetails extends BaseError {

  private static final String DEFAULT_ERROR_MSG = "Something went wrong. Please try again later.";

  private final String message;
  private final String type;
  private final String param;
  private final String code;

  public ErrorDetails(String message) {
    thi