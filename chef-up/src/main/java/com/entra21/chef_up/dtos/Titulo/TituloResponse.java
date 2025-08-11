package com.entra21.chef_up.dtos.Titulo;

public class TituloResponse {
    /// Nome do título
    private String nome;

    /// Condição para desbloquear o título (descrição)
    private String condicaoDesbloqueio;

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
