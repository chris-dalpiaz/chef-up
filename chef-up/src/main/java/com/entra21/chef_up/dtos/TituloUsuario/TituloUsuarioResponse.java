package com.entra21.chef_up.dtos.TituloUsuario;


import com.entra21.chef_up.dtos.Titulo.TituloResponse;

import java.time.LocalDateTime;

public class TituloUsuarioResponse {

    private TituloResponse titulo;

    private LocalDateTime desbloqueadoEm;

    public TituloResponse getTitulo() {
        return titulo;
    }

    public void setTitulo(TituloResponse titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getDesbloqueadoEm() {
        return desbloqueadoEm;
    }

    public void setDesbloqueadoEm(LocalDateTime desbloqueadoEm) {
        this.desbloqueadoEm = desbloqueadoEm;
    }
}
