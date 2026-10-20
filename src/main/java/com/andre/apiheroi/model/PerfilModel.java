package com.andre.apiheroi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PerfilModel {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    private String email;

    @OneToMany(mappedBy = "perfil")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<HeroiModel> herois = new ArrayList<>();

    public PerfilModel() {
    }

    public PerfilModel(Long id, String email, List<HeroiModel> herois) {
        this.id = id;
        this.email = email;
        this.herois = herois;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<HeroiModel> getHerois() {
        return herois;
    }

    public void setHerois(List<HeroiModel> herois) {
        this.herois = herois;
    }
}