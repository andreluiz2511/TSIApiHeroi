package com.andre.apiheroi.assembler;

import com.andre.apiheroi.controller.EquipeController;
import com.andre.apiheroi.model.EquipeModel;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EquipeAssembler {

    public EntityModel<EquipeModel> toModel(EquipeModel equipe) {

        return EntityModel.of(equipe,

                linkTo(methodOn(EquipeController.class)
                        .findAll(Pageable.unpaged()))
                        .withRel("Todas as Equipes")
                        .withType("GET"),

                linkTo(methodOn(EquipeController.class)
                        .findByNome(equipe.getNome(), Pageable.unpaged()))
                        .withRel("Buscar por Nome")
                        .withType("GET"),

                linkTo(methodOn(EquipeController.class)
                        .findById(equipe.getId()))
                        .withSelfRel()
                        .withType("GET"),

                linkTo(methodOn(EquipeController.class)
                        .adicionarHeroi(equipe.getId(), 1L))
                        .withRel("Adicionar Herói - substitua o ID")
                        .withType("POST"),

                linkTo(methodOn(EquipeController.class)
                        .update(equipe, equipe.getId()))
                        .withRel("Atualizar Equipe")
                        .withType("PUT"),

                linkTo(methodOn(EquipeController.class)
                        .delete(equipe.getId()))
                        .withRel("Deletar Equipe")
                        .withType("DELETE")
        );
    }
}