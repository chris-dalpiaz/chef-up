package com.entra21.chef_up.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Utensilio {
    /// ID único do utensílio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome do utensílio
    private String nome;

    /** Retorna o ID do utensílio */
    public Integer getId() {
        return id;
    }

    /** Define o ID do utensílio */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Retorna o nome do utensílio */
    public String getNome() {
        return nome;
    }

    /** Define o nome do utensílio */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
