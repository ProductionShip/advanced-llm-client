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

  public OpenAIImageUrl(String mediaType, byte[] imageData, ImageDetail detail) {
    this.setImageUrl(mediaType, imageData);
    this.detail = detail;
  }

  public OpenAIImageUrl(String mediaType, byte[] imageData) {
    this.setImageUrl(mediaType, imageData);
  }

  public OpenAIImageUrl(String imageUrl, ImageDetail detail) {
    this.url = imageUrl;
    this.detail = detail;
  }

  public OpenAIImageUrl(String imageUrl) {
    this.url = imageUrl;
  }

  public OpenAIImageUrl() {
  }

  public ImageDetail getDetail() {
    return detail;
  }

  public void setDetail(ImageDetail detail) {
    this.detail = detai