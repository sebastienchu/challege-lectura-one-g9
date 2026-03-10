package com.alura.literatura.literatura.repository;

import com.alura.literatura.literatura.model.Book;
import com.alura.literatura.literatura.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.authors LEFT JOIN FETCH b.languages")
    List<Book> findAllWithAuthorsAndLanguages();

    @Query("""
        SELECT DISTINCT a.name
        FROM Book b
        JOIN b.authors a
    """)
    List<String> findDistinctAutores();

    @Query("""
        SELECT DISTINCT a
        FROM Book b
        JOIN b.authors a
        WHERE a.birthYear <= :year
          AND (a.deathYear IS NULL OR a.deathYear >= :year)
    """)
    List<Person> findAutoresVivosEn(@Param("year") int year);

    List<Book> findByLanguagesContainingIgnoreCase(String language);

    // 6 - Top 10 por descargas
    List<Book> findTop10ByOrderByDownloadCountDesc();

    // 7 - Buscar autor por nombre (busca dentro del Set<Person>)
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Book> findByAuthorNameContaining(String nombre);

    // 5 - Por idioma (esto ya lo tenías, pero por si falta)
    @Query("SELECT b FROM Book b JOIN b.languages lang WHERE lang = :idioma")
    List<Book> findByLanguage(String idioma);

}
