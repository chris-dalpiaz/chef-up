package com.entra21.chef_up.dtos.TituloUsuario;

import java.time.LocalDateTime;

public class TituloUsuarioRequest {
    /// Referência ao título associado
    private Integer idTitulo;


    public Integer getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(Integer idTitulo) {
        this.idTitulo = idTitulo;
    }
}
