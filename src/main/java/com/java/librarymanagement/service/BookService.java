package com.java.librarymanagement.service;

import com.java.librarymanagement.DTO.AuthorWithBooksDTO;
import com.java.librarymanagement.DTO.BookWithAuthorsDTO;
import com.java.librarymanagement.DTO.UpdateBookRequest;
import com.java.librarymanagement.entity.Authors;
import com.java.librarymanagement.entity.Book;
import com.java.librarymanagement.repo.AuthorsRepository;
import com.java.librarymanagement.repo.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorsRepository authorsRepository;

    @Transactional
    public List<Book> getAllBooks() {
        return  bookRepository.findAllBooks();
    }

    @Transactional
    public BookWithAuthorsDTO getBookById(Long id) {
        return bookRepository.findAllBooksWithAuthors().stream()
                .map(BookWithAuthorsDTO::map).filter(book -> book.id().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));
    }

    @Transactional
    public List<BookWithAuthorsDTO> getAllBooksWithAuthor() {
        return bookRepository.findAllBooksWithAuthors().stream()
                .map(BookWithAuthorsDTO::map).collect(Collectors.toList());
    }

    @Transactional
    public List<AuthorWithBooksDTO> getAllBooksWithAuthorId(Long id) {
        return getBooksByAuthorId(id);
    }

    @Transactional
    public List<AuthorWithBooksDTO> getBooksByAuthorId(Long id) {
        if (!authorsRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id: " + id);
        }

        return bookRepository.findBookByAuthorsId(id).stream()
                .flatMap(book -> book.getAuthors().stream()
                        .filter(author -> author.getId().equals(id))
                        .map(author -> AuthorWithBooksDTO.map(book, author)))
                .collect(Collectors.toList());
    }

    @Transactional
    public BookWithAuthorsDTO updateBookAndAuthor(Long id, UpdateBookRequest request) {
        Book bookToBeUpdated = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));

        bookToBeUpdated.setBookName(request.bookName());
        bookToBeUpdated.setPublishedDate(request.publishedDate());
        bookToBeUpdated.setPrice(request.price());

        if (request.authorIds() != null) {
            Set<Authors> authors = new HashSet<>(authorsRepository.findAllById(request.authorIds()));
            if (authors.size() != request.authorIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more authors were not found");
            }
            bookToBeUpdated.setAuthors(authors);
        }

        return BookWithAuthorsDTO.map(bookRepository.save(bookToBeUpdated));
    }
}
