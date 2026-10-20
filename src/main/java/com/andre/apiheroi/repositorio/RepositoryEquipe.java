package com.andre.apiheroi.repositorio;

import com.andre.apiheroi.model.EquipeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryEquipe extends JpaRepository<EquipeModel, Long> {

    Page<EquipeModel> findByNomeContainingIgnoreCase(
            String nome, Pageable pageable);
}