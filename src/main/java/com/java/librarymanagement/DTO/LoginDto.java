package com.java.librarymanagement.DTO;

import com.java.librarymanagement.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link Users}
 */
public record LoginDto
        (@NotNull(message = "Email cannot be null")
         @Email(message = "Email must be valid")
         String email,
         @NotNull(message = "Password cannot be null")
         String password
        ) {
}