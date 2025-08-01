package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class TituloUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "titulos_id")
    private Titulos titulo;

    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuarios usuario;

    private Date desbloqueadoEm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Titulos getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulos titulo) {
        this.titulo = titulo;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Date getDesbloqueadoEm() {
        return desbloqueadoEm;
    }

    public void setDesbloqueadoEm(Date desbloqueadoEm) {
        this.desbloqueadoEm = desbloqueadoEm;
    }
}
