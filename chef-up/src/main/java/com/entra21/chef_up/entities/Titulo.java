package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String condicaoDesbloqueio;

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCondicaoDesbloqueio() {
        return condicaoDesbloqueio;
    }

    public void setCondicaoDesbloqueio(String condicaoDesbloqueio) {
        this.condicaoDesbloqueio = condicaoDesbloqueio;
    }
}
