package com.entra21.chef_up.dtos.Receita;


public class ReceitaRequest {

    private String nome;

    private String descricao;

    private Integer tempoPreparoSegundos;

    private String dificuldade;

    private Integer xpGanho;

    private Integer idCategoria;

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

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
}
