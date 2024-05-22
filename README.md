
<a name="readme-top"><\/a>
# CodeGPT - Advanced LLM Client
An enhanced Java HTTP client offering access to large language model APIs & services in a user-friendly manner.

[![Contributions welcome][contributions-welcome-svg]][contributions-welcome]
[![Maven][maven-shield]][maven-url]

## Installation
To integrate the package, make use of the following Maven dependency:

```xml
<dependency>
    <groupId>ee.ProductionShip</groupId>
    <artifactId>advanced-llm-client</artifactId>
    <version>0.8.6</version>
</dependency>
```
Gradle dependency:
```kts
dependencies {
  implementation("ee.ProductionShip:advanced-llm-client:0.8.6")
}
```

## Usage

### Chat Completion SSE
```java
OpenAIClient client = new OpenAIClient.Builder(System.getenv("OPENAI_API_KEY"))
    .setOrganization("MY_ORGANIZATION")
    .build();

EventSource call = client.getChatCompletionAsync(
    new OpenAIChatCompletionRequest.Builder(List.of(new OpenAIChatCompletionStandardMessage("user", prompt)))
        .setModel(OpenAIChatCompletionModel.GPT_4)
        .setTemperature(0.1)
        .build(),
    new CompletionEventListener<String>(){
      @Override
      public void onMessage(String message, EventSource eventSource) {
        System.out.println(message);
      }
    });

call.cancel();
```

## Contributing

Your contributions make the open source realm a wonderful platform for knowledge, inspiration and creativity. All contributions are **deeply valued**.

Should you have a suggestion that could enhance this, please fork the repo and create a pull request. You may also open an issue with the tag "enhancement".

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License
This is an independent OpenAI library and has no association with or endorsement by OpenAI.

MIT © [ProductionShip]


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributions-welcome-svg]: http://img.shields.io/badge/contributions-welcome-brightgreen
[contributions-welcome]: https://github.com/JetBrains/ideavim/blob/master/CONTRIBUTING.md
[maven-shield]: https://img.shields.io/maven-central/v/ee.ProductionShip/advanced-llm-client
[maven-url]: https://central.sonatype.com/namespace/ee.ProductionShip