package ee.carlrobert.llm.client.google;

import static ee.carlrobert.llm.client.DeserializationUtil.OBJECT_MAPPER;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import ee.carlrobert.llm.PropertiesLoader;
import ee.carlrobert.llm.client.DeserializationUtil;
import ee.carlrobert.llm.client.google.completion.ApiResponseError;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionContent;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionRequest;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionResponse;
import ee.carlrobert.llm.client.google.completion.GoogleCompletionResponse.Candidate;
import ee.carlrobert.llm.client.google.completion.GoogleContentPart;
import ee.carlrobert.llm.client.google.embedding.ContentEmbedding;
import ee.carlrobert.llm.client.google.embedding.GoogleBatchEmbeddingResponse;
import ee.carlrobert.llm.client.google.embedding.GoogleEmbeddingContentRequest;
import ee.carlrobert.llm.client.google.embedding.GoogleEmbeddingRequest;
import ee.carlrobert.llm.client.google.embedding.GoogleEmbeddingResponse;
import ee.carlrobert.llm.client.google.mod