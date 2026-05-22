package com.framegenesis.framegenesisai.dto;

public record AuthResponse(
        String token,
        UserResponse user
) {
}
