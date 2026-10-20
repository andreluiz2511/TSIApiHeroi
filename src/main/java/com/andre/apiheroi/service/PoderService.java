package com.andre.apiheroi.service;

import com.andre.apiheroi.excecao.notFoundException;
import com.andre.apiheroi.model.HeroiModel;
import com.andre.apiheroi.model.PoderModel;
import com.andre.apiheroi.repositorio.RepositoryPoder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PoderService {

    private final RepositoryPoder repository;
    private final HeroiService heroiService;

    public PoderService(
            RepositoryPoder repository,
            HeroiService heroiService) {
        this.repository = repository;
        this.heroiService = heroiService;
    }

    public Page<PoderModel> findAll(Pageable pagina) {
        return repository.findAll(pagina);
    }

    public Page<PoderModel> findByName(
            String nome, Pageable pagina) {
        return repository.findByNomeContainingIgnoreCase(
                nome, pagina);
    }

    public PoderModel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new notFoundException(
                                "Poder Não Encontrado"));
    }

    // Cria um poder associado obrigatoriamente a um herói existente.
    public PoderModel create(PoderModel poder, Long heroiId) {

        HeroiModel heroi = heroiService.findById(heroiId);

        poder.setHeroi(heroi);

        return repository.save(poder);
    }

    public PoderModel update(PoderModel poder, Long id) {

        PoderModel salvarPoder = findById(id);

        salvarPoder.setNome(poder.getNome());
        salvarPoder.setDescricao(poder.getDescricao());

        if (poder.getHeroi() != null
                && poder.getHeroi().getId() != null) {

            HeroiModel heroi = heroiService.findById(
                    poder.getHeroi().getId());

            salvarPoder.setHeroi(heroi);
        }

        return repository.save(salvarPoder);
    }

    public void delete(Long id) {
        PoderModel verificarPoder = findById(id);
        repository.delete(verificarPoder);
    }
}