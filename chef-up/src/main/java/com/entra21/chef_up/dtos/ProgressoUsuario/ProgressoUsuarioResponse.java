package com.entra21.chef_up.dtos.ProgressoUsuario;

import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;

import java.time.LocalDateTime;

public class ProgressoUsuarioResponse {

    private Integer nivel;

    private Integer xp;

    private LocalDateTime atualizadoEm;

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
