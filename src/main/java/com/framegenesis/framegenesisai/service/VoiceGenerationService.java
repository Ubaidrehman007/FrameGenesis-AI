package com.framegenesis.framegenesisai.service;

import org.springframework.stereotype.Service;

@Service
public class VoiceGenerationService {

    public String generateVoiceover(String script) {
        return "https://framegenesis.ai/audio/generated-voice.mp3";
    }
}
