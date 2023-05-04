package ee.carlrobert.llm.client.ollama.completion.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OllamaChatCompletionMessage {

  private final String role;
  private final String content;
  private final List<String> images;

 