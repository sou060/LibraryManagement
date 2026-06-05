package com.java.librarymanagement.controller;

import com.java.librarymanagement.DTO.AuthorWithBooksDTO;
import com.java.librarymanagement.DTO.CreateAuthorRequest;
import com.java.librarymanagement.advice.ApiResponse;
import com.java.librarymanagement.entity.Authors;
import com.java.librarymanagement.service.AuthorsService;
import com.java.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorsController {
    private final AuthorsService authorsService;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Authors>>> getAllAuthors() {
        List<Authors> authors = authorsService.getAllAuthors();
        return ResponseEntity.ok(new ApiResponse<>(authors, "Authors retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Authors>> getAuthorById(@PathVariable Long id) {
        Authors author = authorsService.getAuthorById(id);
        return ResponseEntity.ok(new ApiResponse<>(author, "Author retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Authors>> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        Authors newAuthor = new Authors();
        newAuthor.setAuthorName(request.authorName());
        newAuthor.setAuthorEmail(request.authorEmail());
        Authors createdAuthor = authorsService.createAuthor(newAuthor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdAuthor, "Author created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Authors>> updateAuthor(@PathVariable Long id, @Valid @RequestBody CreateAuthorRequest authorDetails) {
        Authors updateFields = new Authors();
        updateFields.setAuthorName(authorDetails.authorName());
        updateFields.setAuthorEmail(authorDetails.authorEmail());
        Authors updatedAuthor = authorsService.updateAuthor(id, updateFields);
        return ResponseEntity.ok(new ApiResponse<>(updatedAuthor, "Author updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAuthor(@PathVariable Long id) {
        authorsService.deleteAuthor(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "Author deleted successfully"));
    }

    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<List<Authors>>> findAuthorsByName(@RequestParam String name) {
        List<Authors> authors = authorsService.findAuthorsByName(name);
        return ResponseEntity.ok(new ApiResponse<>(authors, "Authors retrieved successfully"));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<List<AuthorWithBooksDTO>>> getBooksByAuthorId(@PathVariable Long id) {
        List<AuthorWithBooksDTO> books = bookService.getBooksByAuthorId(id);
        return ResponseEntity.ok(new ApiResponse<>(books, "Books retrieved successfully"));
    }
}



