package ee.carlrobert.llm.client.openai.completion.request;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Base64;

/**
 * Messages with image content are supported by OpenAIs Vision models.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenAIImageUrl {

  private String url;

  private ImageDetail detail;

  public OpenAIImageUrl(String mediaType, byte[] imageData, ImageDetail det