package com.andre.apiheroi.repositorio;

import com.andre.apiheroi.model.PerfilModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPerfil extends JpaRepository<PerfilModel, Long> {

    Page<PerfilModel> findByEmailContainingIgnoreCase(
            String email, Pageable pageable);
}