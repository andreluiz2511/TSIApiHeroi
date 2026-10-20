package com.andre.apiheroi.service;

import com.andre.apiheroi.excecao.notFoundException;
import com.andre.apiheroi.model.PerfilModel;
import com.andre.apiheroi.repositorio.RepositoryPerfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    private final RepositoryPerfil repository;

    public PerfilService(RepositoryPerfil repository) {
        this.repository = repository;
    }

    public Page<PerfilModel> findAll(Pageable pagina) {
        Page<PerfilModel> pagePerfil = repository.findAll(pagina);
        return pagePerfil;
    }

    public Page<PerfilModel> findByEmail(String email, Pageable pagina) {
        Page<PerfilModel> pagePerfil =
                repository.findByEmailContainingIgnoreCase(email, pagina);
        return pagePerfil;
    }

    public PerfilModel findById(Long id) {
        PerfilModel perfil = repository.findById(id)
                .orElseThrow(() -> new notFoundException("Perfil Não Encontrado"));
        return perfil;
    }

    public PerfilModel create(PerfilModel perfil) {
        PerfilModel createPerfil = repository.save(perfil);
        return createPerfil;
    }

    public PerfilModel update(PerfilModel perfil, Long id) {
        PerfilModel salvarPerfil = findById(id);
        salvarPerfil.setEmail(perfil.getEmail());
        salvarPerfil.setHerois(perfil.getHerois());
        return repository.save(salvarPerfil);
    }

    public void delete(Long id) {
        PerfilModel verificarPerfil = findById(id);
        repository.delete(verificarPerfil);
    }
}