package com.alura.literatura.repository;

import com.alura.literatura.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByNameIgnoreCase(String name);
    // para buscar autores vivos
    List<AuthorEntity> findByDeathYearIsNull();
}
