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
    private Usuarios usuario;

    @ManyToOne()
    @JoinColumn(name = "ingredientes_id")
    private Ingredientes ingrediente;

    private Date dataAdicionada;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Ingredientes getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingredientes ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Date getDataAdicionada() {
        return dataAdicionada;
    }

    public void setDataAdicionada(Date dataAdicionada) {
        this.dataAdicionada = dataAdicionada;
    }
}
