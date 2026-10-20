package com.andre.apiheroi.service;

import com.andre.apiheroi.excecao.notFoundException;
import com.andre.apiheroi.model.HeroiModel;
import com.andre.apiheroi.model.PerfilModel;
import com.andre.apiheroi.repositorio.RepositoryHeroi;
import com.andre.apiheroi.repositorio.RepositoryPerfil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HeroiService {

    private final RepositoryHeroi repository;
    private final RepositoryPerfil perfilRepository;

    public HeroiService(
            RepositoryHeroi repository,
            RepositoryPerfil perfilRepository) {
        this.repository = repository;
        this.perfilRepository = perfilRepository;
    }

    public Page<HeroiModel> findAll(Pageable pagina) {
        return repository.findAll(pagina);
    }

    public Page<HeroiModel> findByName(
            String nome, Pageable pagina) {
        return repository.findByNomeContainingIgnoreCase(
                nome, pagina);
    }

    public HeroiModel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new notFoundException(
                                "Herói Não Encontrado"));
    }

    // Cria um herói associado obrigatoriamente a um perfil existente.
    public HeroiModel create(HeroiModel heroi, Long perfilId) {

        PerfilModel perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() ->
                        new notFoundException(
                                "Perfil Não Encontrado"));

        heroi.setPerfil(perfil);

        return repository.save(heroi);
    }

    public HeroiModel update(HeroiModel heroi, Long id) {
        HeroiModel salvarHeroi = findById(id);

        salvarHeroi.setNome(heroi.getNome());
        salvarHeroi.setNomeHeroico(heroi.getNomeHeroico());
        salvarHeroi.setNivelForca(heroi.getNivelForca());

        // Se for informado um novo perfil, verifica se ele existe.
        if (heroi.getPerfil() != null
                && heroi.getPerfil().getId() != null) {

            PerfilModel perfil = perfilRepository
                    .findById(heroi.getPerfil().getId())
                    .orElseThrow(() ->
                            new notFoundException(
                                    "Perfil Não Encontrado"));

            salvarHeroi.setPerfil(perfil);
        }

        return repository.save(salvarHeroi);
    }

    public void delete(Long id) {
        HeroiModel verificarHeroi = findById(id);
        repository.delete(verificarHeroi);
    }
}