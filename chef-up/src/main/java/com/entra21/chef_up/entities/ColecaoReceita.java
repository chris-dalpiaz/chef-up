package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class ColecaoReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receitas receita;

    @ManyToOne()
    @JoinColumn(name = "colecoes_id")
    private Colecoes colecao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Receitas getReceita() {
        return receita;
    }

    public void setReceita(Receitas receita) {
        this.receita = receita;
    }

    public Colecoes getColecao() {
        return colecao;
    }

    public void setColecao(Colecoes colecao) {
        this.colecao = colecao;
    }
}
