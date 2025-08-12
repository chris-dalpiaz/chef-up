package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class Colecao {
    /// Identificador único da coleção
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome da coleção
    private String nome;

    /// Usuário dono da coleção (relacionamento muitos-para-um)
    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    /**
     * Retorna o ID da coleção.
     *
     * @return id da coleção
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da coleção.
     *
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o nome da coleção.
     *
     * @return nome da coleção
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da coleção.
     *
     * @param nome novo nome da coleção
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o usuário dono da coleção.
     *
     * @return usuário dono da coleção
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário dono da coleção.
     *
     * @param usuario usuário que possui a coleção
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
