package com.alura.literatura.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "languages")
@NoArgsConstructor
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // ej: "en", "es"

    @ManyToMany(mappedBy = "languages")
    private Set<BookEntity> books;
}
