
package com.andre.apiheroi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToOne;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class HeroiModel {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O nome heroico é obrigatório")
    @Size(max = 100, message = "O nome heroico deve ter no máximo 100 caracteres")
    private String nomeHeroico;

    @NotNull(message = "O nível de força é obrigatório")
    @Enumerated(EnumType.STRING)
    private NivelForca nivelForca;

    // Todo herói precisa estar associado a um perfil existente.
    @Schema(hidden = true)
    @ManyToOne(optional = false)
    @JoinColumn(name = "perfil_id", nullable = false)
    @JsonIgnore
    private PerfilModel perfil;

    @JsonIgnore
    @ManyToMany(mappedBy = "herois")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<EquipeModel> equipes = new ArrayList<>();

    @OneToOne(mappedBy = "heroi")
    @JsonIgnoreProperties({"heroi"})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PoderModel poder;

    @OneToOne(mappedBy = "heroi")
    @JsonIgnoreProperties({"heroi"})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private FraquezaModel fraqueza;

    public HeroiModel(Long id, String nome, String nomeHeroico) {
        this.id = id;
        this.nome = nome;
        this.nomeHeroico = nomeHeroico;
    }

    public HeroiModel() {
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

    public String getNomeHeroico() {
        return nomeHeroico;
    }

    public void setNomeHeroico(String nomeHeroico) {
        this.nomeHeroico = nomeHeroico;
    }

    public NivelForca getNivelForca() {
        return nivelForca;
    }

    public void setNivelForca(NivelForca nivelForca) {
        this.nivelForca = nivelForca;
    }

    public PerfilModel getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilModel perfil) {
        this.perfil = perfil;
    }

    public List<EquipeModel> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<EquipeModel> equipes) {
        this.equipes = equipes;
    }

    public PoderModel getPoder() {
        return poder;
    }

    public void setPoder(PoderModel poder) {
        this.poder = poder;
    }

    public FraquezaModel getFraqueza() {
        return fraqueza;
    }

    public void setFraqueza(FraquezaModel fraqueza) {
        this.fraqueza = fraqueza;
    }
}
