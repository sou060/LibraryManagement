package com.java.librarymanagement.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record UpdateBookRequest(
        @NotNull(message = "Book name cannot be null")
        String bookName,
        @NotNull(message = "PublishedDate cannot be null")
        LocalDate publishedDate,
        @NotNull(message = "Price cannot be null")
        Double price,
        Set<Long> authorIds
) {
}
