
package com.andre.apiheroi.service;

import com.andre.apiheroi.excecao.notFoundException;
import com.andre.apiheroi.model.FraquezaModel;
import com.andre.apiheroi.model.HeroiModel;
import com.andre.apiheroi.repositorio.RepositoryFraqueza;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FraquezaService {

    private final RepositoryFraqueza repository;
    private final HeroiService heroiService;

    public FraquezaService(
            RepositoryFraqueza repository,
            HeroiService heroiService) {
        this.repository = repository;
        this.heroiService = heroiService;
    }

    public Page<FraquezaModel> findAll(Pageable pagina) {
        return repository.findAll(pagina);
    }

    public Page<FraquezaModel> findByName(
            String nome, Pageable pagina) {
        return repository.findByNomeContainingIgnoreCase(
                nome, pagina);
    }

    public FraquezaModel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new notFoundException(
                                "Fraqueza Não Encontrada"));
    }

    // Cria uma fraqueza associada a um herói existente.
    public FraquezaModel create(
            FraquezaModel fraqueza, Long heroiId) {

        HeroiModel heroi = heroiService.findById(heroiId);

        fraqueza.setHeroi(heroi);

        return repository.save(fraqueza);
    }

    public FraquezaModel update(
            FraquezaModel fraqueza, Long id) {

        FraquezaModel salvarFraqueza = findById(id);

        salvarFraqueza.setNome(fraqueza.getNome());
        salvarFraqueza.setDescricao(fraqueza.getDescricao());

        return repository.save(salvarFraqueza);
    }

    public void delete(Long id) {
        FraquezaModel verificarFraqueza = findById(id);
        repository.delete(verificarFraqueza);
    }
}
