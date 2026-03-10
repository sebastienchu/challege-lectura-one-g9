package com.alura.literatura.repository;

import com.alura.literatura.model.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    Optional<SubjectEntity> findByNameIgnoreCase(String name);
}
