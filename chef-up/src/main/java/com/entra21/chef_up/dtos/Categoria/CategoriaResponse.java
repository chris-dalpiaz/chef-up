package com.entra21.chef_up.dtos.Categoria;

public class CategoriaResponse {

    private String nome;

    private String iconeUrl;

    public String getIconeUrl() {
        return iconeUrl;
    }

    public void setIconeUrl(String iconeUrl) {
        this.iconeUrl = iconeUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
