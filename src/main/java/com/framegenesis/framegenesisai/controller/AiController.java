package com.framegenesis.framegenesisai.controller;

import com.framegenesis.framegenesisai.dto.ScriptRequest;
import com.framegenesis.framegenesisai.dto.ScriptResponse;
import com.framegenesis.framegenesisai.service.GeminiService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final GeminiService geminiService;

    public AiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/generate-script")
    public ScriptResponse generateScript(
            @Valid @RequestBody ScriptRequest request
    ) {

        String script =
                geminiService.generateScript(
                        request.prompt()
                );

        return new ScriptResponse(script);
    }

}
