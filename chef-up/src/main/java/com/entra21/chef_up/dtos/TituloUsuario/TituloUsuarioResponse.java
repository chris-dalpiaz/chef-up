package com.entra21.chef_up.dtos.TituloUsuario;


import com.entra21.chef_up.dtos.Titulo.TituloResponse;

import java.time.LocalDateTime;

public class TituloUsuarioResponse {
    /// Referência ao título associado
    private TituloResponse titulo;

    /// Data que o título foi desbloqueado pelo usuário
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
