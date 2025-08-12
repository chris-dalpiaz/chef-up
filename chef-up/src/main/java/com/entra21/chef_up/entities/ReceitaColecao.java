package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class ReceitaColecao {
    /// ID único da associação entre receita e coleção
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Receita associada (muitos para um)
    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receita receita;

    /// Coleção associada (muitos para um)
    @ManyToOne()
    @JoinColumn(name = "colecoes_id")
    private Colecao colecao;

    /**
     * Retorna o ID da associação
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da associação
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna a receita associada
     */
    public Receita getReceita() {
        return receita;
    }

    /**
     * Define a receita associada
     */
    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    /**
     * Retorna a coleção associada
     */
    public Colecao getColecao() {
        return colecao;
    }

    /**
     * Define a coleção associada
     */
    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }
}
