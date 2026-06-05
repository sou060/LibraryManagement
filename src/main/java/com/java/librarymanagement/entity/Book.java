package com.java.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "Book name cannot be null")
    private String bookName;
    @Column(nullable = false)
    @NotNull(message = "PublishedDate cannot be null")
    private LocalDate publishedDate;
    @Column(nullable = false)
    @NotNull(message = "Price cannot be null")
    private Double price;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    @JsonIgnore
    private Set<Authors> authors = new HashSet<>();

}
