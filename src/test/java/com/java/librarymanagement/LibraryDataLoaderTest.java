package com.java.librarymanagement;

import com.java.librarymanagement.entity.Authors;
import com.java.librarymanagement.entity.Book;
import com.java.librarymanagement.repo.AuthorsRepository;
import com.java.librarymanagement.repo.BookRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Transactional
@Rollback(false)
class LibraryDataLoaderTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorsRepository authorRepository;

    @Test
    void loadData() {

        Authors martin = new Authors(null, "Robert C. Martin",
                "unclebob@gmail.com", new HashSet<>());

        Authors bloch = new Authors(null, "Joshua Bloch",
                "jbloch@gmail.com", new HashSet<>());

        Authors fowler = new Authors(null, "Martin Fowler",
                "fowler@gmail.com", new HashSet<>());

        Authors horstmann = new Authors(null, "Cay Horstmann",
                "horstmann@gmail.com", new HashSet<>());

        Authors evans = new Authors(null, "Eric Evans",
                "evans@gmail.com", new HashSet<>());

        Authors gamma = new Authors(null, "Erich Gamma",
                "gamma@gmail.com", new HashSet<>());

        Authors hunt = new Authors(null, "Andrew Hunt",
                "hunt@gmail.com", new HashSet<>());

        Authors thomas = new Authors(null, "David Thomas",
                "thomas@gmail.com", new HashSet<>());

        Authors goetz = new Authors(null, "Brian Goetz",
                "goetz@gmail.com", new HashSet<>());

        Authors walls = new Authors(null, "Craig Walls",
                "walls@gmail.com", new HashSet<>());

        authorRepository.saveAll(List.of(
                martin, bloch, fowler, horstmann, evans,
                gamma, hunt, thomas, goetz, walls
        ));

        List<Book> books = List.of(

                createBook("Clean Code",
                        LocalDate.of(2008, 8, 1),
                        650.0, martin),

                createBook("Clean Architecture",
                        LocalDate.of(2017, 9, 20),
                        750.0, martin),

                createBook("The Clean Coder",
                        LocalDate.of(2011, 5, 13),
                        550.0, martin),

                createBook("Effective Java",
                        LocalDate.of(2018, 1, 6),
                        899.0, bloch),

                createBook("Java Puzzlers",
                        LocalDate.of(2005, 7, 24),
                        699.0, bloch),

                createBook("Refactoring",
                        LocalDate.of(2018, 11, 19),
                        999.0, fowler),

                createBook("UML Distilled",
                        LocalDate.of(2003, 6, 15),
                        499.0, fowler),

                createBook("Patterns of Enterprise Application Architecture",
                        LocalDate.of(2002, 11, 15),
                        1199.0, fowler),

                createBook("Core Java Volume I",
                        LocalDate.of(2021, 3, 10),
                        1099.0, horstmann),

                createBook("Core Java Volume II",
                        LocalDate.of(2021, 3, 10),
                        1099.0, horstmann),

                createBook("Java for the Impatient",
                        LocalDate.of(2014, 8, 27),
                        649.0, horstmann),

                createBook("Domain Driven Design",
                        LocalDate.of(2003, 8, 30),
                        1299.0, evans),

                createBook("Design Patterns",
                        LocalDate.of(1994, 10, 21),
                        1499.0, gamma),

                createBook("The Pragmatic Programmer",
                        LocalDate.of(1999, 10, 30),
                        899.0, hunt, thomas),

                createBook("Java Concurrency in Practice",
                        LocalDate.of(2006, 5, 19),
                        999.0, goetz),

                createBook("Spring in Action",
                        LocalDate.of(2022, 1, 1),
                        899.0, walls),

                createBook("Head First Java",
                        LocalDate.of(2022, 2, 10),
                        799.0, bloch),

                createBook("Microservices Patterns",
                        LocalDate.of(2019, 11, 1),
                        1199.0, fowler),

                createBook("Working Effectively with Legacy Code",
                        LocalDate.of(2004, 9, 22),
                        899.0, martin),

                createBook("Agile Principles Patterns and Practices",
                        LocalDate.of(2006, 7, 15),
                        949.0, martin)
        );

        bookRepository.saveAll(books);
    }

    private Book createBook(String name,
                            LocalDate date,
                            Double price,
                            Authors... authors) {

        Book book = new Book();
        book.setBookName(name);
        book.setPublishedDate(date);
        book.setPrice(price);

        book.getAuthors().addAll(Set.of(authors));

        return book;
    }
}
