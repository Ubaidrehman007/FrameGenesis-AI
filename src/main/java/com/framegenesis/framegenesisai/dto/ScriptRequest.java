package com.framegenesis.framegenesisai.dto;

import jakarta.validation.constraints.NotBlank;

public record ScriptRequest(
        @NotBlank String prompt
) {
}
