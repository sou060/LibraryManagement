package com.java.librarymanagement.repo;

import com.java.librarymanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    boolean existsByEmail(String email);
}