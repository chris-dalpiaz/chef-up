package com.entra21.chef_up.dtos.Titulo;

public class TituloResponse {

    private Integer id;

    private String nome;

    private String condicaoDesbloqueio;

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
