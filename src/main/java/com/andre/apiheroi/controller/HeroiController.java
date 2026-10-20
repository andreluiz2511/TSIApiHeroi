
package com.andre.apiheroi.controller;

import com.andre.apiheroi.assembler.HeroiAssembler;
import com.andre.apiheroi.model.HeroiModel;
import com.andre.apiheroi.service.HeroiService;
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
@RequestMapping("/heroi")
public class HeroiController {

    private final HeroiService service;
    private final HeroiAssembler assembler;

    public HeroiController(
            HeroiAssembler assembler,
            HeroiService service) {
        this.assembler = assembler;
        this.service = service;
    }

    @Tag(name = "Procurar todos",
            description = "Procurar todos os registros")
    @Operation(summary = "Procurar todos os heróis")
    @ApiResponse(responseCode = "200",
            description = "Heróis encontrados", content = @Content)
    @ApiResponse(responseCode = "400",
            description = "Bad Request", content = @Content)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<HeroiModel>>> findAll(
            @PageableDefault(size = 10) Pageable pagina) {

        Page<HeroiModel> heroiModel = service.findAll(pagina);

        PagedModel<EntityModel<HeroiModel>> listaEntity =
                PagedModel.of(
                        heroiModel.getContent()
                                .stream()
                                .map(assembler::toModel)
                                .toList(),
                        new PagedModel.PageMetadata(
                                heroiModel.getSize(),
                                heroiModel.getNumber(),
                                heroiModel.getTotalElements(),
                                heroiModel.getTotalPages()
                        )
                );

        return new ResponseEntity<>(listaEntity, HttpStatus.OK);
    }

    @Tag(name = "Procurar por Nome",
            description = "Procurar registros por nome")
    @Operation(summary = "Procurar heróis por nome")
    @ApiResponse(responseCode = "200",
            description = "Heróis encontrados", content = @Content)
    @ApiResponse(responseCode = "400",
            description = "Bad Request", content = @Content)
    @GetMapping("/searchByName")
    public ResponseEntity<PagedModel<EntityModel<HeroiModel>>> findByName(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pagina) {

        Page<HeroiModel> heroiModel =
                service.findByName(nome, pagina);

        PagedModel<EntityModel<HeroiModel>> listaEntity =
                PagedModel.of(
                        heroiModel.getContent()
                                .stream()
                                .map(assembler::toModel)
                                .toList(),
                        new PagedModel.PageMetadata(
                                heroiModel.getSize(),
                                heroiModel.getNumber(),
                                heroiModel.getTotalElements(),
                                heroiModel.getTotalPages()
                        )
                );

        return new ResponseEntity<>(listaEntity, HttpStatus.OK);
    }

    @Tag(name = "Procurar por ID",
            description = "Procurar registros por ID")
    @Operation(summary = "Procurar herói por ID")
    @ApiResponse(responseCode = "200",
            description = "Herói encontrado", content = @Content)
    @ApiResponse(responseCode = "400",
            description = "Bad Request", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<HeroiModel>> findById(
            @PathVariable Long id) {

        HeroiModel heroiModel = service.findById(id);

        return new ResponseEntity<>(
                assembler.toModel(heroiModel),
                HttpStatus.OK
        );
    }

    @Tag(name = "Criar", description = "Criar registros")
    @Operation(summary = "Criar herói")
    @ApiResponse(responseCode = "201",
            description = "Herói criado", content = @Content)
    @ApiResponse(responseCode = "400",
            description = "Bad Request", content = @Content)
    @PostMapping
    public ResponseEntity<EntityModel<HeroiModel>> create(
            @RequestBody @Valid HeroiModel heroi,
            @RequestParam Long perfilId) {

        HeroiModel heroiModel = service.create(heroi, perfilId);

        return new ResponseEntity<>(
                assembler.toModel(heroiModel),
                HttpStatus.CREATED
        );
    }

    @Tag(name = "Atualizar", description = "Atualizar registros")
    @Operation(summary = "Atualizar herói")
    @ApiResponse(responseCode = "200",
            description = "Herói atualizado", content = @Content)
    @ApiResponse(responseCode = "400",
            description = "Bad Request", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HeroiModel>> update(
            @RequestBody @Valid HeroiModel heroi,
            @PathVariable Long id) {

        HeroiModel heroiModel = service.update(heroi, id);

        return new ResponseEntity<>(
                assembler.toModel(heroiModel),
                HttpStatus.OK
        );
    }

    @Tag(name = "Deletar", description = "Deletar registros")
    @Operation(summary = "Deletar herói")
    @ApiResponse(responseCode = "204",
            description = "Herói deletado", content = @Content)
    @ApiResponse(responseCode = "400",
            description = "Bad Request", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
