package com.java.librarymanagement.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CreateBookRequest(
        @NotNull(message = "Book name cannot be null")
        String bookName,
        @NotNull(message = "PublishedDate cannot be null")
        LocalDate publishedDate,
        @NotNull(message = "Price cannot be null")
        Double price,
        @NotNull(message = "Author IDs cannot be null")
        Set<Long> authorIds
) {
}

