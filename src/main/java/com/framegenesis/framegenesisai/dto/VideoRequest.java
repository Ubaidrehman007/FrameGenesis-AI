package com.framegenesis.framegenesisai.dto;

import jakarta.validation.constraints.NotBlank;

public record VideoRequest(
        @NotBlank String prompt
) {
}
