package com.java.librarymanagement.controller;

import com.java.librarymanagement.DTO.AuthorWithBooksDTO;
import com.java.librarymanagement.entity.Authors;
import com.java.librarymanagement.service.AuthorsService;
import com.java.librarymanagement.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuthorsController {
    private final AuthorsService authorsService;
    private final BookService bookService;

    @GetMapping("/authors/{id}")
    public Authors getAuthorById(@PathVariable Long id) {
        return authorsService.getAuthorById(id);
    }

    @GetMapping("/authors/{id}/books")
    public List<AuthorWithBooksDTO> getBooksByAuthorId(@PathVariable Long id) {
        return bookService.getBooksByAuthorId(id);
    }
}
