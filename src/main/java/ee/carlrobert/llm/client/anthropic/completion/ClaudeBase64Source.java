package ee.carlrobert.llm.client.anthropic.completion;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Base64;

/**
 * Represents a base64-encoded data source in Claude.
 * <br>
 * Allowed media types include the following:
 * image/jpeg, image/png, image/gif, and im