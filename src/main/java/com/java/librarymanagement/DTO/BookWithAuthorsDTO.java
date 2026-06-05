package com.java.librarymanagement.DTO;

import com.java.librarymanagement.entity.Book;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record BookWithAuthorsDTO(
        Long id,
        String bookName,
        LocalDate publishedDate,
        Double price,
        Set<AuthorsDto> authors
) {
    public static BookWithAuthorsDTO map(Book book) {
        return new BookWithAuthorsDTO(
                book.getId(),
                book.getBookName(),
                book.getPublishedDate(),
                book.getPrice(),
                book.getAuthors().stream().map(AuthorsDto::map).collect(Collectors.toSet())
        );
    }
}
