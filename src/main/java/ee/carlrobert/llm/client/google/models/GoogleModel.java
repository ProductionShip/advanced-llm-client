package ee.carlrobert.llm.client.google.models;

import java.util.Arrays;

public enum GoogleModel {
  GEMINI_1_0_PRO("gemini-1.0-pro", "Gemini 1.0 Pro (32k)", 30720 + 2048),
  GEMINI_1_0_PRO_001("gemini-1.0-pro-001", "Gemini 1.0 Pro 001 (32k)", 30720 + 2048),
  GEMINI_1_0_PRO_LATEST("gemini-1.0-pro-latest", "Gemini 1.0 Pro Latest (32k)", 30720 + 2048),
  GEMINI_1_0_PRO_VISION_LATEST("gemini-1.0-pro-vision-latest", "Gemini 1.0 Pro Vision Latest (16k)",
      12288 + 4096),
  GEMINI_PRO("gemini-pro", "Gemini Pro (32k)", 30720 + 