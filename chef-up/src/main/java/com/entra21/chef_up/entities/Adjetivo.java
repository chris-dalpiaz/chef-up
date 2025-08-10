package com.entra21.chef_up.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Adjetivo {
    /// Identificador único do adjetivo (chave primária)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome do adjetivo
    private String nome;

    /**
     * Retorna o ID do adjetivo.
     * @return id do adjetivo
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do adjetivo.
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do adjetivo.
     * @return nome do adjetivo
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do adjetivo.
     * @param nome novo nome para o adjetivo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
