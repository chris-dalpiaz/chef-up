package com.entra21.chef_up.dtos.ProgressoUsuario;

import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;

import java.time.LocalDateTime;

public class ProgressoUsuarioResponse {
    /// Usuário ao qual o progresso pertence
    private UsuarioResponse usuario;

    /// Nível atual do usuário
    private Integer nivel;

    /// Pontos de experiência acumulados pelo usuário
    private Integer xp;

    /// Data e hora da última atualização do progresso
    private LocalDateTime atualizadoEm;

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
