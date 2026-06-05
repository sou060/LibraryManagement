package com.java.librarymanagement.repo;

import com.java.librarymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}