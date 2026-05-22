package com.framegenesis.framegenesisai.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneGenerationService {

    public List<String> generateScenes(String script) {

        return List.of(

                "Futuristic city with AI holograms",

                "Robots working alongside humans",

                "AI-powered hospitals and schools",

                "Global AI network transforming humanity"
        );
    }
}