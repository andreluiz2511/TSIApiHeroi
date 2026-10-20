
package com.andre.apiheroi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class FraquezaModel {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome da fraqueza é obrigatório")
    @Size(max = 100, message = "O nome da fraqueza deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "A descrição da fraqueza é obrigatória")
    @Size(max = 500, message = "A descrição da fraqueza deve ter no máximo 500 caracteres")
    private String descricao;

    // Toda fraqueza precisa pertencer a um herói existente.
    @Schema(hidden = true)
    @OneToOne(optional = false)
    @JoinColumn(name = "heroi_id", nullable = false, unique = true)
    @JsonIgnoreProperties({
            "perfil",
            "equipes",
            "poder",
            "fraqueza"
    })
    private HeroiModel heroi;

    public FraquezaModel() {
    }

    public FraquezaModel(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public HeroiModel getHeroi() {
        return heroi;
    }

    public void setHeroi(HeroiModel heroi) {
        this.heroi = heroi;
    }
}
