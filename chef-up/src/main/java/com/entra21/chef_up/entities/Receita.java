package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private Integer tempoPreparoSegundos;

    private String dificuldade;

    private Integer xpGanho;

    @ManyToOne()
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL)
    private List<IngredienteReceita> ingredientes;

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<IngredienteReceita> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteReceita> ingredientes) {
        this.ingredientes = ingredientes;
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTempoPreparoSegundos() {
        return tempoPreparoSegundos;
    }

    public void setTempoPreparoSegundos(Integer tempoPreparoSegundos) {
        this.tempoPreparoSegundos = tempoPreparoSegundos;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Integer getXpGanho() {
        return xpGanho;
    }

    public void setXpGanho(Integer xpGanho) {
        this.xpGanho = xpGanho;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
