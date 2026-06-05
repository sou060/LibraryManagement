package com.java.librarymanagement.service;

import com.java.librarymanagement.entity.Authors;
import com.java.librarymanagement.entity.Book;
import com.java.librarymanagement.repo.AuthorsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthorsServiceTest {
    @Test
    void deleteAuthorRemovesAuthorFromAssociatedBooksBeforeDeleting() {
        Authors author = new Authors();
        author.setId(1L);

        Book book = new Book();
        book.setId(10L);
        book.setAuthors(new HashSet<>());
        book.getAuthors().add(author);

        author.setBook(new HashSet<>());
        author.getBook().add(book);

        AtomicReference<Authors> deletedAuthor = new AtomicReference<>();
        AuthorsRepository authorsRepository = stubAuthorsRepository(Map.of(1L, author), deletedAuthor);
        AuthorsService authorsService = new AuthorsService(authorsRepository);

        authorsService.deleteAuthor(1L);

        assertFalse(book.getAuthors().contains(author));
        assertFalse(author.getBook().contains(book));
        assertFalse(deletedAuthor.get().getBook().contains(book));
    }

    @Test
    void deleteAuthorThrowsNotFoundWhenAuthorDoesNotExist() {
        AuthorsRepository authorsRepository = stubAuthorsRepository(Map.of(), new AtomicReference<>());
        AuthorsService authorsService = new AuthorsService(authorsRepository);

        assertThrows(ResponseStatusException.class, () -> authorsService.deleteAuthor(99L));
    }

    private AuthorsRepository stubAuthorsRepository(Map<Long, Authors> authorsById, AtomicReference<Authors> deletedAuthor) {
        return (AuthorsRepository) Proxy.newProxyInstance(
                AuthorsRepository.class.getClassLoader(),
                new Class<?>[]{AuthorsRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findById" -> Optional.ofNullable(authorsById.get((Long) args[0]));
                    case "delete" -> {
                        deletedAuthor.set((Authors) args[0]);
                        yield null;
                    }
                    case "toString" -> "StubAuthorsRepository";
                    default -> throw new UnsupportedOperationException(method.getName());
                }
        );
    }
}
