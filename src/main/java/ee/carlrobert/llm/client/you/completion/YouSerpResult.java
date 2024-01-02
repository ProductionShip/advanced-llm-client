package ee.carlrobert.llm.client.you.completion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YouSerpResult {

  private final String url;
  private final String name;
  private final String snippet;
  private final String snippetSource;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public YouSerpResul