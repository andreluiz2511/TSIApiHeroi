package com.andre.apiheroi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class EquipeModel {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome da equipe é obrigatório")
    @Size(max = 100, message = "O nome da equipe deve ter no máximo 100 caracteres")
    private String nome;

    // Perfil responsável pela equipe
    @ManyToOne(optional = false)
    @JoinColumn(name = "perfil_id", nullable = false)
    @JsonIgnore
    @Schema(hidden = true)
    private PerfilModel perfil;

    // Uma equipe pode ter vários heróis,
    // e um herói pode participar de várias equipes
    @ManyToMany
    @JoinTable(
            name = "equipe_heroi",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "heroi_id")
    )
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnoreProperties({"equipes", "perfil"})
    private List<HeroiModel> herois = new ArrayList<>();

    public EquipeModel() {
    }

    public EquipeModel(Long id, String nome, PerfilModel perfil,
                       List<HeroiModel> herois) {
        this.id = id;
        this.nome = nome;
        this.perfil = perfil;
        this.herois = herois;
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

    public PerfilModel getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilModel perfil) {
        this.perfil = perfil;
    }

    public List<HeroiModel> getHerois() {
        return herois;
    }

    public void setHerois(List<HeroiModel> herois) {
        this.herois = herois;
    }
}