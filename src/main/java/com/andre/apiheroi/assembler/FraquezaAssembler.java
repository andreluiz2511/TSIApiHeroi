
package com.andre.apiheroi.assembler;

import com.andre.apiheroi.controller.FraquezaController;
import com.andre.apiheroi.model.FraquezaModel;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FraquezaAssembler {

    public EntityModel<FraquezaModel> toModel(FraquezaModel fraqueza) {

        return EntityModel.of(
                fraqueza,

                linkTo(methodOn(FraquezaController.class)
                        .findAll(Pageable.unpaged()))
                        .withRel("Todas as Fraquezas")
                        .withType("GET"),

                linkTo(methodOn(FraquezaController.class)
                        .findByName(fraqueza.getNome(), Pageable.unpaged()))
                        .withRel("Buscar por Nome")
                        .withType("GET"),

                linkTo(methodOn(FraquezaController.class)
                        .findById(fraqueza.getId()))
                        .withSelfRel()
                        .withType("GET"),

                linkTo(methodOn(FraquezaController.class)
                        .update(fraqueza, fraqueza.getId()))
                        .withRel("Atualiza Fraqueza")
                        .withType("UPDATE"),

                linkTo(methodOn(FraquezaController.class)
                        .delete(fraqueza.getId()))
                        .withRel("Deleta Fraqueza")
                        .withType("DELETE")
        );
    }
}
