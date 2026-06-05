package com.java.librarymanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "Author name cannot be null")
    private String authorName;
    @Column(nullable = false)
    @NotNull(message = "Author email cannot be null")
    @Email(message = "Please enter a valid email")
    private String authorEmail;
    @ManyToMany(mappedBy = "authors",cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private Set<Book> book=new HashSet<>();
}
