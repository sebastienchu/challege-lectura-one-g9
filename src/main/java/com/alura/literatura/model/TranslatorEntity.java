package com.alura.literatura.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "translators")
@NoArgsConstructor
public class TranslatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer birthYear;
    private Integer deathYear;

    @ManyToMany(mappedBy = "translators")
    private Set<BookEntity> books;
}
