package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class Receita {
    /// ID único da receita
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome da receita
    private String nome;

    /// Descrição da receita
    private String descricao;

    /// Tempo de preparo em segundos
    private Integer tempoPreparoSegundos;

    /// Dificuldade da receita (ex: fácil, médio, difícil)
    private String dificuldade;

    /// XP ganho ao completar a receita
    private Integer xpGanho;

    /// Categoria da receita (relacionamento muitos para um)
    @ManyToOne()
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    /** Retorna o ID da receita */
    public Integer getId() {
        return id;
    }

    /** Define o ID da receita */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Retorna o nome da receita */
    public String getNome() {
        return nome;
    }

    /** Define o nome da receita */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** Retorna a descrição da receita */
    public String getDescricao() {
        return descricao;
    }

    /** Define a descrição da receita */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /** Retorna o tempo de preparo em segundos */
    public Integer getTempoPreparoSegundos() {
        return tempoPreparoSegundos;
    }

    /** Define o tempo de preparo em segundos */
    public void setTempoPreparoSegundos(Integer tempoPreparoSegundos) {
        this.tempoPreparoSegundos = tempoPreparoSegundos;
    }

    /** Retorna a dificuldade da receita */
    public String getDificuldade() {
        return dificuldade;
    }

    /** Define a dificuldade da receita */
    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    /** Retorna o XP ganho */
    public Integer getXpGanho() {
        return xpGanho;
    }

    /** Define o XP ganho */
    public void setXpGanho(Integer xpGanho) {
        this.xpGanho = xpGanho;
    }

    /** Retorna a categoria da receita */
    public Categoria getCategoria() {
        return categoria;
    }

    /** Define a categoria da receita */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
