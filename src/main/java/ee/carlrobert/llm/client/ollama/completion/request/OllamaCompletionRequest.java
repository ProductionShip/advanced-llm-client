
package ee.carlrobert.llm.client.ollama.completion.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ee.carlrobert.llm.client.ollama.completion.response.OllamaResponseFormat;
import ee.carlrobert.llm.completion.CompletionRequest;

/*
 * See <a href="https://github.com/ollama/ollama/blob/main/docs/api.md#generate-a-completion">ollama/api</a>
 */
@JsonInclude(Include.NON_NULL)
public class OllamaCompletionRequest implements CompletionRequest {

  private final String model;
  private final String prompt;
