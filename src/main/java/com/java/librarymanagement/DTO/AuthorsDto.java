package com.java.librarymanagement.DTO;

import com.java.librarymanagement.entity.Authors;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java.librarymanagement.entity.Authors}
 */
@Value
public class AuthorsDto implements Serializable {
    Long id;
    @NotNull(message = "Author name cannot be null")
    String authorName;
    @NotNull(message = "Author email cannot be null")
    @Email(message = "Please enter a valid email")
    String authorEmail;

    public static AuthorsDto map(Authors authors) {
        return new AuthorsDto(
                authors.getId(),
                authors.getAuthorName(),
                authors.getAuthorEmail()
        );
    }
}
