package com.java.librarymanagement.repo;

import com.java.librarymanagement.entity.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors, Long> {
}