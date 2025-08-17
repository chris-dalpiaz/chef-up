package com.entra21.chef_up.dtos.Receita;

import com.entra21.chef_up.dtos.Categoria.CategoriaResponse;
import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaResponse;
import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaResponse;
import com.entra21.chef_up.entities.EtapaReceita;
import com.entra21.chef_up.entities.IngredienteReceita;

import java.util.List;

public class ReceitaResponse {

    private String nome;

    private String descricao;

    private Integer tempoPreparoSegundos;

    private String dificuldade;

    private Integer xpGanho;

    private CategoriaResponse categoria;

    private List<IngredienteReceitaResponse> ingredientes;

    private List<UtensilioReceitaResponse> utensilios;

    private List<EtapaReceitaResponse> etapas;

    public List<EtapaReceitaResponse> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<EtapaReceitaResponse> etapas) {
        this.etapas = etapas;
    }

    public List<IngredienteReceitaResponse> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteReceitaResponse> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<UtensilioReceitaResponse> getUtensilios() {
        return utensilios;
    }

    public void setUtensilios(List<UtensilioReceitaResponse> utensilios) {
        this.utensilios = utensilios;
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

    public CategoriaResponse getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponse categoria) {
        this.categoria = categoria;
    }
}
