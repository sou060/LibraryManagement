package com.java.librarymanagement.controller;

import com.java.librarymanagement.DTO.AuthorWithBooksDTO;
import com.java.librarymanagement.DTO.BookWithAuthorsDTO;
import com.java.librarymanagement.DTO.UpdateBookRequest;
import com.java.librarymanagement.entity.Book;
import com.java.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BooksController {
    private final BookService bookService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public BookWithAuthorsDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/allBooksWithAuthor")
    public List<BookWithAuthorsDTO> getAllBooksWithAuthor() {
        return bookService.getAllBooksWithAuthor();
    }
    @GetMapping("/allBooksWithAuthor/{id}")
    public List<AuthorWithBooksDTO> getAllBooksWithAuthor1(@PathVariable Long id) {
        return bookService.getAllBooksWithAuthorId(id);
    }
    @PutMapping("/books/{id}")
    public BookWithAuthorsDTO updateBookAndAuthor(@PathVariable Long id, @Valid @RequestBody UpdateBookRequest request) {
        return bookService.updateBookAndAuthor(id, request);
    }
}
