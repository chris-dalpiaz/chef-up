package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ProgressoUsuario {
    /// ID único do progresso do usuário
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Usuário ao qual o progresso pertence
    @OneToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    /// Nível atual do usuário
    private Integer nivel;

    /// Pontos de experiência acumulados pelo usuário
    private Integer xp;

    /// Data e hora da última atualização do progresso
    private LocalDateTime atualizadoEm;

    /**
     * Retorna o ID do progresso
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do progresso
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
     * Retorna o nível atual
     */
    public Integer getNivel() {
        return nivel;
    }

    /**
     * Define o nível atual
     */
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    /**
     * Retorna o XP atual
     */
    public Integer getXp() {
        return xp;
    }

    /**
     * Define o XP atual
     */
    public void setXp(Integer xp) {
        this.xp = xp;
    }

    /**
     * Retorna a data da última atualização
     */
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    /**
     * Define a data da última atualização
     */
    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
