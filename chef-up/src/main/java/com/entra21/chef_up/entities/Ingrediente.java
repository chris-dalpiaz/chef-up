package com.entra21.chef_up.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String categoria;

    private Integer estimativaValidade;

    private String dicaConservacao;

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getEstimativaValidade() {
        return estimativaValidade;
    }

    public void setEstimativaValidade(Integer estimativaValidade) {
        this.estimativaValidade = estimativaValidade;
    }

    public String getDicaConservacao() {
        return dicaConservacao;
    }

    public void setDicaConservacao(String dicaConservacao) {
        this.dicaConservacao = dicaConservacao;
    }
}
