package com.andre.apiheroi.assembler;

import com.andre.apiheroi.controller.HeroiController;
import com.andre.apiheroi.model.HeroiModel;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class HeroiAssembler {

    public EntityModel<HeroiModel> toModel(HeroiModel heroi) {
        return EntityModel.of(
                heroi,

                linkTo(methodOn(HeroiController.class)
                        .findAll(Pageable.unpaged()))
                        .withRel("Todos os Heróis")
                        .withType("GET"),

                linkTo(methodOn(HeroiController.class)
                        .findByName(
                                heroi.getNome(),
                                Pageable.unpaged()))
                        .withRel("Buscar por Nome")
                        .withType("GET"),

                linkTo(methodOn(HeroiController.class)
                        .findById(heroi.getId()))
                        .withSelfRel()
                        .withType("GET"),

                linkTo(methodOn(HeroiController.class)
                        .update(heroi, heroi.getId()))
                        .withRel("Atualiza Herói")
                        .withType("PUT"),

                linkTo(methodOn(HeroiController.class)
                        .delete(heroi.getId()))
                        .withRel("Deleta Herói")
                        .withType("DELETE")
        );
    }
}