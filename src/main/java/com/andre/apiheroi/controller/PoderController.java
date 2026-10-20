package com.andre.apiheroi.controller;

import com.andre.apiheroi.assembler.PoderAssembler;
import com.andre.apiheroi.model.PoderModel;
import com.andre.apiheroi.service.PoderService;

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
@RequestMapping("/poder")
public class PoderController {

    private final PoderService service;
    private final PoderAssembler assembler;

    public PoderController(
            PoderAssembler assembler,
            PoderService service) {
        this.assembler = assembler;
        this.service = service;
    }

    @Tag(name = "Procurar todos", description = "Procurar todos os poderes")
    @Operation(summary = "Procurar todos os poderes dos Heróis")
    @ApiResponse(responseCode = "200", description = "Poderes encontrados", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PoderModel>>> findAll(
            @PageableDefault(size = 10) Pageable pagina) {

        Page<PoderModel> poderModel = service.findAll(pagina);

        PagedModel<EntityModel<PoderModel>> listaEntity = PagedModel.of(
                poderModel.getContent().stream()
                        .map(assembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        poderModel.getSize(),
                        poderModel.getNumber(),
                        poderModel.getTotalElements(),
                        poderModel.getTotalPages()
                )
        );

        return ResponseEntity.ok(listaEntity);
    }

    @Tag(name = "Procurar por Nome", description = "Procurar poderes por nome")
    @Operation(summary = "Procurar poderes por nome")
    @ApiResponse(responseCode = "200", description = "Busca realizada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @GetMapping("/searchByName")
    public ResponseEntity<PagedModel<EntityModel<PoderModel>>> findByName(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pagina) {

        Page<PoderModel> poderModel = service.findByName(nome, pagina);

        PagedModel<EntityModel<PoderModel>> listaEntity = PagedModel.of(
                poderModel.getContent().stream()
                        .map(assembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        poderModel.getSize(),
                        poderModel.getNumber(),
                        poderModel.getTotalElements(),
                        poderModel.getTotalPages()
                )
        );

        return ResponseEntity.ok(listaEntity);
    }

    @Tag(name = "Procurar por ID", description = "Procurar poder por ID")
    @Operation(summary = "Procurar poder por ID")
    @ApiResponse(responseCode = "200", description = "Poder encontrado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PoderModel>> findById(
            @PathVariable Long id) {

        PoderModel poderModel = service.findById(id);

        return ResponseEntity.ok(assembler.toModel(poderModel));
    }

    @Tag(name = "Criar", description = "Criar registros")
    @Operation(summary = "Criar poder associado a um herói")
    @ApiResponse(responseCode = "201", description = "Poder criado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @PostMapping
    public ResponseEntity<EntityModel<PoderModel>> create(
            @RequestBody @Valid PoderModel poder,
            @RequestParam Long heroiId) {

        PoderModel poderModel = service.create(poder, heroiId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(poderModel));
    }

    @Tag(name = "Atualizar", description = "Atualizar registros")
    @Operation(summary = "Atualizar poder")
    @ApiResponse(responseCode = "200", description = "Poder atualizado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PoderModel>> update(
            @RequestBody @Valid PoderModel poder,
            @PathVariable Long id) {

        PoderModel poderModel = service.update(poder, id);

        return ResponseEntity.ok(assembler.toModel(poderModel));
    }

    @Tag(name = "Deletar", description = "Deletar registros")
    @Operation(summary = "Deletar poder")
    @ApiResponse(responseCode = "204", description = "Poder deletado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}