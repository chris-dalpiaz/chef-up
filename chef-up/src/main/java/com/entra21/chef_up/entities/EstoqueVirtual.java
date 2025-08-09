package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class EstoqueVirtual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne()
    @JoinColumn(name = "ingredientes_id")
    private Ingrediente ingrediente;

    private Date dataAdicionada;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Date getDataAdicionada() {
        return dataAdicionada;
    }

    public void setDataAdicionada(Date dataAdicionada) {
        this.dataAdicionada = dataAdicionada;
    }
}
