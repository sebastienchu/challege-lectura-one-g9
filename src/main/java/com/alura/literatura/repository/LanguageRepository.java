package com.alura.literatura.repository;

import com.alura.literatura.model.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

    Optional<LanguageEntity> findByCodeIgnoreCase(String code);
}
