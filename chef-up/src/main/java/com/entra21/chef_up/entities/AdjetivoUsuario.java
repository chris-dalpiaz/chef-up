package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class AdjetivoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "adjetivos_id")
    private Adjetivos adjetivo;

    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuarios usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Adjetivos getAdjetivo() {
        return adjetivo;
    }

    public void setAdjetivo(Adjetivos adjetivo) {
        this.adjetivo = adjetivo;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
