package com.alura.literatura.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "formats")
@NoArgsConstructor
public class FormatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mime;
    private String url;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;
}
