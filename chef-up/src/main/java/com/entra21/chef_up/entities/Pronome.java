package com.entra21.chef_up.entities;

import com.entra21.chef_up.dtos.Pronome.PronomeResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pronome {
    /// ID único do pronome
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome do pronome (ex: ele, ela, elu)
    private String nome;

    /**
     * Retorna o ID do pronome
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do pronome
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome do pronome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do pronome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
