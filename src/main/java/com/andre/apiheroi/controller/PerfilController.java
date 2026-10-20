package com.andre.apiheroi.controller;

import com.andre.apiheroi.assembler.PerfilAssembler;
import com.andre.apiheroi.model.PerfilModel;
import com.andre.apiheroi.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    private final PerfilService service;
    private final PerfilAssembler assembler;

    public PerfilController(PerfilAssembler assembler, PerfilService service) {
        this.assembler = assembler;
        this.service = service;
    }

    @Tag(name = "Procurar todos", description = "Procurar todos os registros")
    @Operation(summary = "Procurar perfis (GET)")
    @ApiResponse(responseCode = "200", description = "Perfis encontrados", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<PerfilModel>>> findAll(
            @PageableDefault(size = 10) Pageable pagina) {

        Page<PerfilModel> perfilModel = service.findAll(pagina);

        PagedModel<EntityModel<PerfilModel>> listaEntity = PagedModel.of(
                perfilModel.getContent().stream()
                        .map(assembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        perfilModel.getSize(),
                        perfilModel.getNumber(),
                        perfilModel.getTotalElements(),
                        perfilModel.getTotalPages()
                )
        );

        return new ResponseEntity<>(listaEntity, HttpStatus.OK);
    }

    @Tag(name = "Procurar por e-mail", description = "Procurar perfis por e-mail")
    @Operation(summary = "Procurar perfil por e-mail (GET)")
    @ApiResponse(responseCode = "200", description = "Busca realizada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    @GetMapping("/searchByEmail")
    public ResponseEntity<PagedModel<EntityModel<PerfilModel>>> findByEmail(
            @RequestParam String email,
            @PageableDefault(size = 10) Pageable pagina) {

        Page<PerfilModel> perfilModel = service.findByEmail(email, pagina);

        PagedModel<EntityModel<PerfilModel>> listaEntity = PagedModel.of(
                perfilModel.getContent().stream()
                        .map(assembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        perfilModel.getSize(),
                        perfilModel.getNumber(),
                        perfilModel.getTotalElements(),
                        perfilModel.getTotalPages()
                )
        );

        return new ResponseEntity<>(listaEntity, HttpStatus.OK);
    }

    @Tag(name = "Procurar por ID", description = "Procurar por ID")
    @Operation(summary = "Procurar perfil por ID (GET)")
    @ApiResponse(responseCode = "200", description = "Perfil encontrado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PerfilModel>> findById(@PathVariable Long id) {

        PerfilModel perfilModel = service.findById(id);

        return new ResponseEntity<>(assembler.toModel(perfilModel), HttpStatus.OK);
    }

    @Tag(name = "Criar", description = "Criar registros")
    @Operation(summary = "Criar perfil")
    @ApiResponse(responseCode = "201", description = "Perfil criado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    @PostMapping()
    public ResponseEntity<EntityModel<PerfilModel>> create(
            @RequestBody @Valid PerfilModel perfil) {

        PerfilModel perfilModel = service.create(perfil);

        return new ResponseEntity<>(assembler.toModel(perfilModel), HttpStatus.CREATED);
    }

    @Tag(name = "Atualizar", description = "Atualizar registros")
    @Operation(summary = "Atualizar perfil")
    @ApiResponse(responseCode = "200", description = "Perfil atualizado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PerfilModel>> update(
            @RequestBody @Valid PerfilModel perfil,
            @PathVariable Long id) {

        PerfilModel perfilModel = service.update(perfil, id);

        return new ResponseEntity<>(assembler.toModel(perfilModel), HttpStatus.OK);
    }

    @Tag(name = "Deletar", description = "Deletar registros")
    @Operation(summary = "Deletar perfil")
    @ApiResponse(responseCode = "204", description = "Perfil deletado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}