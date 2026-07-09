package com.java.librarymanagement.repo;

import com.java.librarymanagement.entity.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors, Long> {
    @Query("SELECT a from Authors a where lower(a.authorName) like lower(concat('%', ?1, '%'))")
    List<Authors> findByAuthorNameContains(String authorName);
}

