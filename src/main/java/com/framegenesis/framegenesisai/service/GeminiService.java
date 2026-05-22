package com.framegenesis.framegenesisai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.model}")
    private String model;

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    public GeminiService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClientBuilder = webClientBuilder;
        this.objectMapper = objectMapper;
    }

    public String generateScript(String prompt) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GEMINI_API_KEY is not configured");
        }

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of(
                        "role", "user",
                        "parts", List.of(Map.of(
                                "text", "Generate a cinematic documentary script for: " + prompt
                        ))
                ))
        );

        String response = webClientBuilder
                .build()
                .post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent", model)
                .header("x-goog-api-key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("Gemini request failed")
                                .flatMap(body -> Mono.error(new IllegalStateException(body)))
                )
                .bodyToMono(String.class)
                .block();

        return extractText(response);
    }

    private String extractText(String response) {
        try {
            JsonNode candidates = objectMapper.readTree(response).path("candidates");

            if (candidates.isArray()) {
                for (JsonNode candidate : candidates) {
                    JsonNode parts = candidate.path("content").path("parts");
                    if (parts.isArray()) {
                        StringBuilder script = new StringBuilder();
                        for (JsonNode part : parts) {
                            JsonNode text = part.get("text");
                            if (text != null && text.isTextual()) {
                                script.append(text.asText());
                            }
                        }

                        if (!script.isEmpty()) {
                            return script.toString();
                        }
                    }
                }
            }

            throw new IllegalStateException("Gemini response did not contain generated text");
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to parse Gemini response", exception);
        }
    }
}
