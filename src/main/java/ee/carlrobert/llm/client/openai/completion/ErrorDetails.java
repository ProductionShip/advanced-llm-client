package ee.carlrobert.llm.client.openai.completion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ee.carlrobert.llm.client.BaseError;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetails extends BaseError {

  private static fin