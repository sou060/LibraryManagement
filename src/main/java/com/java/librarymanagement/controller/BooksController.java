package com.java.librarymanagement.controller;

import com.java.librarymanagement.DTO.AuthorWithBooksDTO;
import com.java.librarymanagement.DTO.BookWithAuthorsDTO;
import com.java.librarymanagement.DTO.CreateBookRequest;
import com.java.librarymanagement.DTO.UpdateBookRequest;
import com.java.librarymanagement.entity.Book;
import com.java.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BooksController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookWithAuthorsDTO> getBookById(@PathVariable Long id) {
        BookWithAuthorsDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookWithAuthorsDTO> createBook(@Valid @RequestBody CreateBookRequest request) {
        BookWithAuthorsDTO createdBook = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookWithAuthorsDTO> updateBook(@PathVariable Long id, @Valid @RequestBody UpdateBookRequest request) {
        BookWithAuthorsDTO updatedBook = bookService.updateBookAndAuthor(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookAndAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/with-authors")
    public ResponseEntity<List<BookWithAuthorsDTO>> getAllBooksWithAuthors() {
        List<BookWithAuthorsDTO> books = bookService.getAllBooksWithAuthor();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> findBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.findBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/published-after")
    public ResponseEntity<List<Book>> findBooksPublishedAfter(@RequestParam String date) {
        java.time.LocalDate localDate = java.time.LocalDate.parse(date);
        List<Book> books = bookService.findBooksPublishedAfter(localDate);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/by-author/{authorId}")
    public ResponseEntity<List<AuthorWithBooksDTO>> getBooksByAuthor(@PathVariable Long authorId) {
        List<AuthorWithBooksDTO> books = bookService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }
}


