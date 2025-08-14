package com.entra21.chef_up.dtos.Ingrediente;

public class IngredienteResponse {

    private String nome;

    private String categoria;

    private Integer estimativaValidade;

    private String dicaConservacao;

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
