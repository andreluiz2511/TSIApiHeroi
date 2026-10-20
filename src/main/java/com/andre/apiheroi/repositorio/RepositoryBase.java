package com.andre.apiheroi.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositoryBase<T> extends JpaRepository<T,Long> {
    Page<T> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
