package com.andre.apiheroi.controller;

import com.andre.apiheroi.assembler.EquipeAssembler;
import com.andre.apiheroi.model.EquipeModel;
import com.andre.apiheroi.service.EquipeService;

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
@RequestMapping("/equipe")
public class EquipeController {

    private final EquipeService service;
    private final EquipeAssembler assembler;

    public EquipeController(
            EquipeAssembler assembler,
            EquipeService service) {

        this.assembler = assembler;
        this.service = service;
    }

    @Tag(name = "Procurar todos", description = "Procurar todas as equipes")
    @Operation(summary = "Procurar equipes")
    @ApiResponse(responseCode = "200", description = "Equipes encontradas",
            content = @Content)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<EquipeModel>>> findAll(
            @PageableDefault(size = 10) Pageable pagina) {

        Page<EquipeModel> equipes = service.findAll(pagina);

        PagedModel<EntityModel<EquipeModel>> resultado = PagedModel.of(
                equipes.getContent().stream()
                        .map(assembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        equipes.getSize(),
                        equipes.getNumber(),
                        equipes.getTotalElements(),
                        equipes.getTotalPages()
                )
        );

        return ResponseEntity.ok(resultado);
    }

    @Tag(name = "Procurar por Nome", description = "Buscar equipes por nome")
    @Operation(summary = "Procurar equipes por nome")
    @GetMapping("/searchByName")
    public ResponseEntity<PagedModel<EntityModel<EquipeModel>>> findByNome(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pagina) {

        Page<EquipeModel> equipes = service.findByNome(nome, pagina);

        PagedModel<EntityModel<EquipeModel>> resultado = PagedModel.of(
                equipes.getContent().stream()
                        .map(assembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        equipes.getSize(),
                        equipes.getNumber(),
                        equipes.getTotalElements(),
                        equipes.getTotalPages()
                )
        );

        return ResponseEntity.ok(resultado);
    }

    @Tag(name = "Procurar por ID", description = "Buscar equipe por ID")
    @Operation(summary = "Procurar equipe por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EquipeModel>> findById(
            @PathVariable Long id) {

        EquipeModel equipe = service.findById(id);

        return ResponseEntity.ok(assembler.toModel(equipe));
    }

    @Tag(name = "Criar", description = "Criar equipe para um perfil")
    @Operation(summary = "Criar equipe vinculada a um perfil")
    @ApiResponse(responseCode = "201", description = "Equipe criada")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PostMapping
    public ResponseEntity<EntityModel<EquipeModel>> create(
            @RequestParam Long perfilId,
            @RequestBody @Valid EquipeModel equipe) {

        EquipeModel equipeCriada = service.create(equipe, perfilId);

        return new ResponseEntity<>(
                assembler.toModel(equipeCriada),
                HttpStatus.CREATED
        );
    }

    @Tag(name = "Adicionar herói", description = "Adicionar herói a uma equipe")
    @Operation(summary = "Adicionar herói à equipe")
    @ApiResponse(responseCode = "200", description = "Seu Herói foi adicionado à equipe")
    @ApiResponse(responseCode = "400", description = "Herói não pertence ao perfil da equipe")
    @PostMapping("/{equipeId}/herois/{heroiId}")
    public ResponseEntity<EntityModel<EquipeModel>> adicionarHeroi(
            @PathVariable Long equipeId,
            @PathVariable Long heroiId) {

        EquipeModel equipeAtualizada =
                service.adicionarHeroi(equipeId, heroiId);

        return ResponseEntity.ok(assembler.toModel(equipeAtualizada));
    }

    @Tag(name = "Atualizar", description = "Atualizar equipe")
    @Operation(summary = "Atualizar equipe")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EquipeModel>> update(
            @RequestBody @Valid EquipeModel equipe,
            @PathVariable Long id) {

        EquipeModel equipeAtualizada = service.update(equipe, id);

        return ResponseEntity.ok(assembler.toModel(equipeAtualizada));
    }

    @Tag(name = "Deletar", description = "Excluir equipe")
    @Operation(summary = "Deletar equipe")
    @ApiResponse(responseCode = "204", description = " Sua Equipe foi deletada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}