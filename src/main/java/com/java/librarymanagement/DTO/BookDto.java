package com.java.librarymanagement.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.java.librarymanagement.entity.Book}
 */
@Value
public class BookDto implements Serializable {
    Long id;
    @NotNull(message = "Book name cannot be null")
    String bookName;
    @NotNull(message = "PublishedDate cannot be null")
    LocalDate publishedDate;
    @NotNull(message = "Price cannot be null")
    Double price;
}