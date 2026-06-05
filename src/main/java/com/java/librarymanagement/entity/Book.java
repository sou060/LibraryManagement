package com.java.librarymanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "Book name cannot be null")
    String bookName;
    @Column(nullable = false)
    @NotNull(message = "PublishedDate cannot be null")
    LocalDate publishedDate;
    @Column(nullable = false)
    @NotNull(message = "Price cannot be null")
    Double price;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private Set<Authors> authors = new HashSet<>();

}
