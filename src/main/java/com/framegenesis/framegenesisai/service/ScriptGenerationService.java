package com.framegenesis.framegenesisai.service;

import org.springframework.stereotype.Service;

@Service
public class ScriptGenerationService {

    private final GeminiService geminiService;

    public ScriptGenerationService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String generateScript(String prompt) {
        return geminiService.generateScript(prompt);
    }
}
