package com.entra21.chef_up.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AvatarUsuario {
    /// Identificador único da relação entre usuário e avatar
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Usuário associado a esse avatar
    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    /// Avatar associado ao usuário
    @ManyToOne()
    @JoinColumn(name = "avatares_id")
    private Avatar avatar;

    /// Data e hora em que o avatar foi desbloqueado pelo usuário
    private LocalDateTime desbloqueadoEm;

    /**
     * Retorna o ID da relação.
     * @return id da relação usuário-avatar
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da relação.
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o usuário associado.
     * @return usuário
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário associado.
     * @param usuario usuário a ser associado
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna o avatar associado.
     * @return avatar
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Define o avatar associado.
     * @param avatar avatar a ser associado
     */
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    /**
     * Retorna a data e hora em que o avatar foi desbloqueado.
     * @return data e hora do desbloqueio
     */
    public LocalDateTime getDesbloqueadoEm() {
        return desbloqueadoEm;
    }

    /**
     * Define a data e hora do desbloqueio do avatar.
     * @param desbloqueadoEm data e hora do desbloqueio
     */
    public void setDesbloqueadoEm(LocalDateTime desbloqueadoEm) {
        this.desbloqueadoEm = desbloqueadoEm;
    }
}
