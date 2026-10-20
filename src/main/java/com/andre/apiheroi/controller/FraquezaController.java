
package com.andre.apiheroi.controller;

import com.andre.apiheroi.assembler.FraquezaAssembler;
import com.andre.apiheroi.model.FraquezaModel;
import com.andre.apiheroi.service.FraquezaService;

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
@RequestMapping("/fraqueza")
public class FraquezaController {

    private final FraquezaService service;
    private final FraquezaAssembler assembler;

    public FraquezaController(
            FraquezaAssembler assembler,
            FraquezaService service) {
        this.assembler = assembler;
        this.service = service;
    }

    @Tag(name = "Procurar todos", description = "Procurar todas as fraquezas")
    @Operation(summary = "Procurar")
    @ApiResponse(responseCode = "200", description = "Fraquezas encontradas", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FraquezaModel>>> findAll(
            @PageableDefault(size = 10) Pageable pagina) {

        Page<FraquezaModel> fraquezas = service.findAll(pagina);

        PagedModel<EntityModel<FraquezaModel>> listaEntity = PagedModel.of(
                fraquezas.getContent().stream()
                        .map(assembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        fraquezas.getSize(),
                        fraquezas.getNumber(),
                        fraquezas.getTotalElements(),
                        fraquezas.getTotalPages()
                )
        );

        return ResponseEntity.ok(listaEntity);
    }

    @Tag(name = "Procurar por Nome", description = "Buscar fraquezas pelo nome")
    @Operation(summary = "Procurar por nome")
    @ApiResponse(responseCode = "200", description = "Busca realizada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @GetMapping("/searchByName")
    public ResponseEntity<PagedModel<EntityModel<FraquezaModel>>> findByName(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pagina) {

        Page<FraquezaModel> fraquezas = service.findByName(nome, pagina);

        PagedModel<EntityModel<FraquezaModel>> listaEntity = PagedModel.of(
                fraquezas.getContent().stream()
                        .map(assembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        fraquezas.getSize(),
                        fraquezas.getNumber(),
                        fraquezas.getTotalElements(),
                        fraquezas.getTotalPages()
                )
        );

        return ResponseEntity.ok(listaEntity);
    }

    @Tag(name = "Procurar por ID", description = "Buscar fraqueza pelo ID")
    @Operation(summary = "Procurar por ID")
    @ApiResponse(responseCode = "200", description = "Sua fraqueza foi encontrada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<FraquezaModel>> findById(
            @PathVariable Long id) {

        FraquezaModel fraqueza = service.findById(id);

        return ResponseEntity.ok(assembler.toModel(fraqueza));
    }

    @Tag(name = "Criar", description = "Criar uma fraqueza")
    @Operation(summary = "Criar")
    @ApiResponse(responseCode = "201", description = "A sua fraqueza  foi criada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @PostMapping
    public ResponseEntity<EntityModel<FraquezaModel>> create(
            @RequestBody @Valid FraquezaModel fraqueza,
            @RequestParam Long heroiId) {

        FraquezaModel fraquezaCriada = service.create(fraqueza, heroiId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(fraquezaCriada));
    }

    @Tag(name = "Atualizar", description = "Atualizar uma fraqueza")
    @Operation(summary = "Atualizar")
    @ApiResponse(responseCode = "200", description = "A sua Fraqueza foi atualizada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FraquezaModel>> update(
            @RequestBody @Valid FraquezaModel fraqueza,
            @PathVariable Long id) {

        FraquezaModel fraquezaAtualizada = service.update(fraqueza, id);

        return ResponseEntity.ok(assembler.toModel(fraquezaAtualizada));
    }

    @Tag(name = "Deletar", description = "Excluir uma fraqueza")
    @Operation(summary = "Deletar")
    @ApiResponse(responseCode = "204", description = "A sua fraqueza foi excluída!", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
