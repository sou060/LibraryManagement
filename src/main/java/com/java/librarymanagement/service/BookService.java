package com.java.librarymanagement.service;

import com.java.librarymanagement.DTO.AuthorWithBooksDTO;
import com.java.librarymanagement.DTO.BookWithAuthorsDTO;
import com.java.librarymanagement.DTO.CreateBookRequest;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorsRepository authorsRepository;
    private final AuthorsService authorsService;

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

        // Store old authors to check for orphans after update
        Set<Authors> oldAuthors = new HashSet<>(bookToBeUpdated.getAuthors());

        bookToBeUpdated.setBookName(request.bookName());
        bookToBeUpdated.setPublishedDate(request.publishedDate());
        bookToBeUpdated.setPrice(request.price());

        if (request.authorIds() != null) {
            Set<Authors> authors = new HashSet<>(authorsRepository.findAllById(request.authorIds()));
            if (authors.size() != request.authorIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more authors were not found");
            }
            bookToBeUpdated.setAuthors(authors);

            // Orphan removal: delete authors that were removed from this book
            Set<Long> newAuthorIds = authors.stream().map(Authors::getId).collect(Collectors.toSet());
            oldAuthors.stream()
                    .filter(author -> !newAuthorIds.contains(author.getId()))
                    .forEach(author -> authorsService.deleteOrphanAuthor(author.getId()));
        }

        return BookWithAuthorsDTO.map(bookRepository.save(bookToBeUpdated));
    }

    @Transactional
    public BookWithAuthorsDTO deleteBookAndAuthor(Long id) {
        Book bookToBeDeleted = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));

        // Store authors before deletion to check for orphans
        Set<Authors> authors = new HashSet<>(bookToBeDeleted.getAuthors());

        bookRepository.delete(bookToBeDeleted);

        // Orphan removal: delete authors that have no other books
        authors.forEach(author -> authorsService.deleteOrphanAuthor(author.getId()));

        return BookWithAuthorsDTO.map(bookToBeDeleted);
    }

    @Transactional
    public BookWithAuthorsDTO createBook(CreateBookRequest request) {
        Set<Authors> authors = new HashSet<>(authorsRepository.findAllById(request.authorIds()));
        if (authors.size() != request.authorIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more authors were not found");
        }

        Book newBook = new Book();
        newBook.setBookName(request.bookName());
        newBook.setPublishedDate(request.publishedDate());
        newBook.setPrice(request.price());
        newBook.setAuthors(authors);

        Book savedBook = bookRepository.save(newBook);
        return BookWithAuthorsDTO.map(savedBook);
    }

    @Transactional
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByBookTitle(title);
    }

    @Transactional
    public List<Book> findBooksPublishedAfter(LocalDate date) {
        return bookRepository.findBooksPublishedAfter(date);
    }
}
