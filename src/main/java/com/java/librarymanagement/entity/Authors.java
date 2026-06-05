package com.java.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @JsonIgnore
    private Set<Book> book=new HashSet<>();
}
