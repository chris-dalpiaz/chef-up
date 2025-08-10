package com.entra21.chef_up.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Titulo {
    /// ID único do título
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome do título
    private String nome;

    /// Condição para desbloquear o título (descrição)
    private String condicaoDesbloqueio;

    /** Retorna o ID do título */
    public Integer getId() {
        return id;
    }

    /** Define o ID do título */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Retorna o nome do título */
    public String getNome() {
        return nome;
    }

    /** Define o nome do título */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** Retorna a condição para desbloqueio do título */
    public String getCondicaoDesbloqueio() {
        return condicaoDesbloqueio;
    }

    /** Define a condição para desbloqueio do título */
    public void setCondicaoDesbloqueio(String condicaoDesbloqueio) {
        this.condicaoDesbloqueio = condicaoDesbloqueio;
    }
}
