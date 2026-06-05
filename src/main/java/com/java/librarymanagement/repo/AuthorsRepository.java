package com.java.librarymanagement.repo;

import com.java.librarymanagement.entity.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository<Authors, Long> {
}