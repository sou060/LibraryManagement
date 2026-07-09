package com.java.librarymanagement.DTO;

public record LoginResponseDto(
        String name,
        String accessToken,
        String refreshToken
) {
}
