
package ee.carlrobert.llm.client;

import static ee.carlrobert.llm.client.util.JSONUtil.jsonMapResponse;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import ee.carlrobert.llm.client.http.ResponseEntity;
import ee.carlrobert.llm.client.http.exchange.BasicHttpExchange;
import ee.carlrobert.llm.client.http.exchange.StreamHttpExchange;
import ee.carlrobert.llm.client.llama.LlamaClient;
import ee.carlrobert.llm.client.llama.completion.LlamaCompletionRequest;
import ee.carlrobert.llm.client.llama.completion.LlamaCompletionRequest.Builder;
import ee.carlrobert.llm.client.llama.completion.LlamaInfillRequest;
import ee.carlrobert.llm.completion.CompletionEventListener;
import java.util.List;
import okhttp3.sse.EventSource;
import org.junit.jupiter.api.Test;

public class LlamaClientTest extends BaseTest {
