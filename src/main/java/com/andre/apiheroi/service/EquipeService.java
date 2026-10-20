package com.andre.apiheroi.service;

import com.andre.apiheroi.excecao.notFoundException;
import com.andre.apiheroi.model.EquipeModel;
import com.andre.apiheroi.model.HeroiModel;
import com.andre.apiheroi.model.PerfilModel;
import com.andre.apiheroi.repositorio.RepositoryEquipe;
import com.andre.apiheroi.repositorio.RepositoryHeroi;
import com.andre.apiheroi.repositorio.RepositoryPerfil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EquipeService {

    private final RepositoryEquipe repository;
    private final RepositoryHeroi repositoryHeroi;
    private final RepositoryPerfil repositoryPerfil;

    public EquipeService(
            RepositoryEquipe repository,
            RepositoryHeroi repositoryHeroi,
            RepositoryPerfil repositoryPerfil) {

        this.repository = repository;
        this.repositoryHeroi = repositoryHeroi;
        this.repositoryPerfil = repositoryPerfil;
    }

    public Page<EquipeModel> findAll(Pageable pagina) {
        return repository.findAll(pagina);
    }

    public Page<EquipeModel> findByNome(String nome, Pageable pagina) {
        return repository.findByNomeContainingIgnoreCase(nome, pagina);
    }

    public EquipeModel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new notFoundException("Equipe Não Encontrada"));
    }

    public EquipeModel create(EquipeModel equipe, Long perfilId) {

        PerfilModel perfil = repositoryPerfil.findById(perfilId)
                .orElseThrow(() ->
                        new notFoundException("Perfil Não Encontrado"));

        equipe.setPerfil(perfil);

        equipe.setHerois(new ArrayList<>());

        return repository.save(equipe);
    }

    public EquipeModel adicionarHeroi(Long equipeId, Long heroiId) {

        EquipeModel equipe = findById(equipeId);

        HeroiModel heroi = repositoryHeroi.findById(heroiId)
                .orElseThrow(() ->
                        new notFoundException(
                                "Herói Não Encontrado: " + heroiId));

        if (equipe.getPerfil() == null || heroi.getPerfil() == null) {
            throw new IllegalArgumentException(
                    "A equipe e o herói precisam estar vinculados a um perfil.");
        }

        if (!equipe.getPerfil().getId()
                .equals(heroi.getPerfil().getId())) {

            throw new IllegalArgumentException(
                    "Só é permitido adicionar heróis cadastrados " +
                            "no perfil que criou a equipe.");
        }

        if (equipe.getHerois() == null) {
            equipe.setHerois(new ArrayList<>());
        }

        boolean jaAdicionado = equipe.getHerois().stream()
                .anyMatch(h -> h.getId().equals(heroiId));

        if (!jaAdicionado) {
            equipe.getHerois().add(heroi);
        }

        return repository.save(equipe);
    }

    public EquipeModel update(EquipeModel equipe, Long id) {

        EquipeModel equipeSalva = findById(id);
        equipeSalva.setNome(equipe.getNome());

        return repository.save(equipeSalva);
    }

    public void delete(Long id) {
        EquipeModel equipe = findById(id);
        repository.delete(equipe);
    }
}