package com.andre.apiheroi.assembler;

import com.andre.apiheroi.controller.PerfilController;
import com.andre.apiheroi.model.PerfilModel;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PerfilAssembler {

    public EntityModel<PerfilModel> toModel(PerfilModel perfil) {
        return EntityModel.of(perfil,
                linkTo(methodOn(PerfilController.class).findAll(Pageable.unpaged()))
                        .withRel("Todos os Perfis").withType("GET"),

                linkTo(methodOn(PerfilController.class).findByEmail(
                        perfil.getEmail(), Pageable.unpaged()))
                        .withRel("Buscar por Email").withType("GET"),

                linkTo(methodOn(PerfilController.class).findById(perfil.getId()))
                        .withSelfRel().withType("GET"),

                linkTo(methodOn(PerfilController.class).create(perfil))
                        .withRel("Cria Perfil").withType("CREATE"),

                linkTo(methodOn(PerfilController.class).update(perfil, perfil.getId()))
                        .withRel("Atualiza Perfil").withType("UPDATE"),

                linkTo(methodOn(PerfilController.class).delete(perfil.getId()))
                        .withRel("Deleta Perfil").withType("DELETE")
        );
    }
}