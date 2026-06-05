package com.java.librarymanagement.service;

import com.java.librarymanagement.entity.Authors;
import com.java.librarymanagement.entity.Book;
import com.java.librarymanagement.repo.AuthorsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    @Transactional
    public List<Authors> getAllAuthors() {
        return authorsRepository.findAll();
    }

    @Transactional
    public Authors getAuthorById(Long id) {
        return authorsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id: " + id));
    }

    @Transactional
    public Authors createAuthor(Authors author) {
        return authorsRepository.save(author);
    }

    @Transactional
    public Authors updateAuthor(Long id, Authors authorDetails) {
        return authorsRepository.findById(id).map(existingAuthor -> {
            if (authorDetails.getAuthorName() != null) {
                existingAuthor.setAuthorName(authorDetails.getAuthorName());
            }
            if (authorDetails.getAuthorEmail() != null) {
                existingAuthor.setAuthorEmail(authorDetails.getAuthorEmail());
            }
            return authorsRepository.save(existingAuthor);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id: " + id));
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Authors author = authorsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id: " + id));

        Set<Book> books = new HashSet<>(author.getBook());
        books.forEach(book -> book.getAuthors().remove(author));
        author.getBook().clear();

        authorsRepository.delete(author);
    }

    @Transactional
    public List<Authors> findAuthorsByName(String name) {
        return authorsRepository.findByAuthorNameContains(name);
    }

    /**
     * Orphan removal for Authors in many-to-many relationships.
     * Deletes an author if they are no longer associated with any books.
     * This is called when a book is deleted or authors are removed from a book.
     * @param authorId the ID of the author to check and potentially delete
     */
    @Transactional
    public void deleteOrphanAuthor(Long authorId) {
        authorsRepository.findById(authorId).ifPresent(author -> {
            // If the author has no associated books, delete them (orphan removal)
            if (author.getBook().isEmpty()) {
                authorsRepository.deleteById(authorId);
            }
        });
    }
}
