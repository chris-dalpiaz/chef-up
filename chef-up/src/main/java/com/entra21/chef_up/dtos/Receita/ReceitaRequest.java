package com.entra21.chef_up.dtos.Receita;


public class ReceitaRequest {
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

    /// idCategoria da receita (relacionamento muitos para um)
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
