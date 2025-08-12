package com.entra21.chef_up.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ingrediente {
    /// Identificador único do ingrediente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome do ingrediente
    private String nome;

    /// Categoria do ingrediente (ex: Verdura, Fruta, Temperos)
    private String categoria;

    /// Estimativa de validade em dias
    private Integer estimativaValidade;

    /// Dica de conservação para o ingrediente
    private String dicaConservacao;

    /**
     * Retorna o ID do ingrediente.
     *
     * @return id do ingrediente
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do ingrediente.
     *
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do ingrediente.
     *
     * @return nome do ingrediente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do ingrediente.
     *
     * @param nome novo nome do ingrediente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a categoria do ingrediente.
     *
     * @return categoria do ingrediente
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do ingrediente.
     *
     * @param categoria nova categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Retorna a estimativa de validade do ingrediente em dias.
     *
     * @return validade estimada em dias
     */
    public Integer getEstimativaValidade() {
        return estimativaValidade;
    }

    /**
     * Define a estimativa de validade do ingrediente em dias.
     *
     * @param estimativaValidade validade estimada
     */
    public void setEstimativaValidade(Integer estimativaValidade) {
        this.estimativaValidade = estimativaValidade;
    }

    /**
     * Retorna a dica de conservação do ingrediente.
     *
     * @return dica de conservação
     */
    public String getDicaConservacao() {
        return dicaConservacao;
    }

    /**
     * Define a dica de conservação do ingrediente.
     *
     * @param dicaConservacao dica para conservar o ingrediente
     */
    public void setDicaConservacao(String dicaConservacao) {
        this.dicaConservacao = dicaConservacao;
    }
}
