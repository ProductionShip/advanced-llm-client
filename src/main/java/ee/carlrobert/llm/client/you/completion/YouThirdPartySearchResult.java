
package ee.carlrobert.llm.client.you.completion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YouThirdPartySearchResult {

  private final List<YouSerpResult> thirdPartySearchResults;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)