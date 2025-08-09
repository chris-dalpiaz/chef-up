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
    private Titulo titulo;

    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    private Date desbloqueadoEm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDesbloqueadoEm() {
        return desbloqueadoEm;
    }

    public void setDesbloqueadoEm(Date desbloqueadoEm) {
        this.desbloqueadoEm = desbloqueadoEm;
    }
}
