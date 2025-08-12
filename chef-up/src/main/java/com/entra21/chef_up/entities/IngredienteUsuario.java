package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class IngredienteUsuario {
    /// ID único da relação entre usuário e ingrediente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Usuário associado ao ingrediente
    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    /// Ingrediente associado ao usuário
    @ManyToOne()
    @JoinColumn(name = "ingredientes_id")
    private Ingrediente ingrediente;

    /// Data e hora em que o ingrediente foi adicionado pelo usuário
    private LocalDateTime dataAdicionada;

    /**
     * Retorna o ID da relação
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da relação
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o usuário associado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário associado
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna o ingrediente associado
     */
    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    /**
     * Define o ingrediente associado
     */
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    /**
     * Retorna a data em que o ingrediente foi adicionado
     */
    public LocalDateTime getDataAdicionada() {
        return dataAdicionada;
    }

    /**
     * Define a data em que o ingrediente foi adicionado
     */
    public void setDataAdicionada(LocalDateTime dataAdicionada) {
        this.dataAdicionada = dataAdicionada;
    }
}
