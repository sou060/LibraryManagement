package com.java.librarymanagement.DTO;

import com.java.librarymanagement.entity.Authors;
import com.java.librarymanagement.entity.Book;

import java.time.LocalDate;

public record AuthorWithBooksDTO(
        Long authorId,
        String authorName,
        String bookName,
        LocalDate publishedDate,
        Double price) {

    public static AuthorWithBooksDTO map(Book book, Authors authors) {
        return new AuthorWithBooksDTO(
                authors.getId(),
                authors.getAuthorName(),
                book.getBookName(),
                book.getPublishedDate(),
                book.getPrice()
        );
    }
}
