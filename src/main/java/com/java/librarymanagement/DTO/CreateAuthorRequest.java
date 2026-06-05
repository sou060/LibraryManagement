package com.java.librarymanagement.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateAuthorRequest(
        @NotNull(message = "Author name cannot be null")
        String authorName,
        @NotNull(message = "Author email cannot be null")
        @Email(message = "Please enter a valid email")
        String authorEmail
) {
}

