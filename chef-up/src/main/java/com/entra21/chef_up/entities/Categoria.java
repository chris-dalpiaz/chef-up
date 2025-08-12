package com.entra21.chef_up.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Categoria {
    /// Identificador único da categoria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome da categoria
    private String nome;

    /// URL do ícone que representa a categoria
    private String iconeUrl;

    /**
     * Retorna o ID da categoria.
     *
     * @return id da categoria
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da categoria.
     *
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome da categoria.
     *
     * @return nome da categoria
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da categoria.
     *
     * @param nome novo nome da categoria
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a URL do ícone da categoria.
     *
     * @return URL do ícone
     */
    public String getIconeUrl() {
        return iconeUrl;
    }

    /**
     * Define a URL do ícone da categoria.
     *
     * @param iconeUrl URL do ícone
     */
    public void setIconeUrl(String iconeUrl) {
        this.iconeUrl = iconeUrl;
    }
}
