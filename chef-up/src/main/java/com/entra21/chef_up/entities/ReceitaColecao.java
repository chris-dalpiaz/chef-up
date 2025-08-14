package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class ReceitaColecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receita receita;

    @ManyToOne()
    @JoinColumn(name = "colecoes_id")
    private Colecao colecao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }
}
