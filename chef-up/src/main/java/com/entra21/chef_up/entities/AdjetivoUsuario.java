package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "adjetivo_usuario",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"usuario_id", "adjetivo_id"})
        }
)
public class AdjetivoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "adjetivo_id")
    private Adjetivo adjetivo;

    @ManyToOne()
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Adjetivo getAdjetivo() {
        return adjetivo;
    }

    public void setAdjetivo(Adjetivo adjetivo) {
        this.adjetivo = adjetivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
