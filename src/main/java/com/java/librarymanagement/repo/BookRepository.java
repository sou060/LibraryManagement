package com.java.librarymanagement.repo;

import com.java.librarymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT distinct b from Book b LEFT JOIN FETCH b.authors")
    List<Book> findAllBooksWithAuthors();
    @Query("SELECT distinct b from Book b")
    List<Book> findAllBooks();

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.authors a WHERE a.id = :id")
    List<Book> findBookByAuthorsId(@Param("id") Long id);
}