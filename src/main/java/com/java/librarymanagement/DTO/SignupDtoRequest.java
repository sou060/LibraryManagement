package com.java.librarymanagement.DTO;

public record SignupDtoRequest(
        String name,
        String email,
        String password
) {
}
