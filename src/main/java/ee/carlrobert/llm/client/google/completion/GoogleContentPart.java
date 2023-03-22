package ee.carlrobert.llm.client.google.completion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Base64;
import java.util.Base64.Encoder;

@JsonInclude(Include.NON_NULL)
public class GoogleContentPart {

  private String text;
  private Blob inlineData;

  public GoogleContentPart() {
  }

  public GoogleContentPart(String text) {
    this(text, null);
  }

  public GoogleContentPart(String text, Blob inlineData) {
    this.text = text;
    this.inlineData = inlineData;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Blob getInlineData() {
    return inlineData;
  }

  public void setInlineData(Blob inlineData) {
    this.inlineData = inlineData;
  }

  /**
   * <