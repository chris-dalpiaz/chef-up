package com.entra21.chef_up.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Avatar {
    /// Identificador único do avatar (chave primária)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome do avatar
    private String nome;

    /// URL da imagem do avatar
    private String imagemUrl;

    /**
     * Retorna o ID do avatar.
     *
     * @return id do avatar
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do avatar.
     *
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do avatar.
     *
     * @return nome do avatar
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do avatar.
     *
     * @param nome novo nome do avatar
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a URL da imagem do avatar.
     *
     * @return URL da imagem
     */
    public String getImagemUrl() {
        return imagemUrl;
    }

    /**
     * Define a URL da imagem do avatar.
     *
     * @param imagemUrl nova URL da imagem
     */
    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
