package ee.carlrobert.llm.client.http;

public class ResponseEntity {

  private final int statusCode;
  private final String response;

  public ResponseEntity(String response) {
    this(200, response);
  }

  public ResponseEntity(int statusCode, String response) {
    th