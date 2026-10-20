
package com.andre.apiheroi.assembler;

import com.andre.apiheroi.controller.PoderController;
import com.andre.apiheroi.model.PoderModel;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PoderAssembler {

    public EntityModel<PoderModel> toModel(PoderModel poder) {
        return EntityModel.of(
                poder,

                linkTo(methodOn(PoderController.class)
                        .findAll(Pageable.unpaged()))
                        .withRel("Todos os Poderes")
                        .withType("GET"),

                linkTo(methodOn(PoderController.class)
                        .findByName(poder.getNome(), Pageable.unpaged()))
                        .withRel("Buscar por Nome")
                        .withType("GET"),

                linkTo(methodOn(PoderController.class)
                        .findById(poder.getId()))
                        .withSelfRel()
                        .withType("GET"),

                linkTo(methodOn(PoderController.class)
                        .update(poder, poder.getId()))
                        .withRel("Atualiza Poder")
                        .withType("UPDATE"),

                linkTo(methodOn(PoderController.class)
                        .delete(poder.getId()))
                        .withRel("Deleta Poder")
                        .withType("DELETE")
        );
    }
}
