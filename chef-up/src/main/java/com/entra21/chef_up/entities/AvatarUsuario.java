package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AvatarUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne()
    @JoinColumn(name = "avatares_id")
    private Avatar avatar;

    private LocalDateTime desbloqueadoEm;

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

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getDesbloqueadoEm() {
        return desbloqueadoEm;
    }

    public void setDesbloqueadoEm(LocalDateTime desbloqueadoEm) {
        this.desbloqueadoEm = desbloqueadoEm;
    }
}
