package com.entra21.chef_up.dtos.ProgressoUsuario;

import java.time.LocalDateTime;

public class ProgressoUsuarioRequest {
    /// Usuário ao qual o progresso pertence
    private Integer idUsuario;

    /// Nível atual do usuário
    private Integer nivel;

    /// Pontos de experiência acumulados pelo usuário
    private Integer xp;

    /// Data e hora da última atualização do progresso
    private LocalDateTime atualizadoEm;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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
