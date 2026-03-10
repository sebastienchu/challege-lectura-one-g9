package com.alura.literatura.repository;

import com.alura.literatura.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    // búsqueda por título (ya la tenías)
    List<BookEntity> findByTitleContainingIgnoreCase(String title);

    // búsqueda eficiente por nombre de autor (no usar findByAuthorsContainingIgnoreCase porque authors es una colección de AuthorEntity)
    @Query("""
            SELECT b FROM BookEntity b
            JOIN b.authors a
            WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))
            """)
    List<BookEntity> findByAuthorName(@Param("name") String name);

    // búsqueda eficiente por código de idioma (ej: "en", "es")
    @Query("""
           SELECT b FROM BookEntity b
           JOIN b.languages l
           WHERE LOWER(l.code) = LOWER(:code)
           """)
    List<BookEntity> findByLanguageCode(@Param("code") String code);
}
