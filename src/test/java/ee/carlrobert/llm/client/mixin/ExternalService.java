package ee.carlrobert.llm.client.mixin;

import ee.carlrobert.llm.client.http.Service;

public enum ExternalService implements Service {
  CODEGPT("codegpt.baseUrl"),
  OPENAI("openai.baseUrl"),
  ANTHROPIC("anthropic.baseUrl"),
  AZURE("azure.openai.