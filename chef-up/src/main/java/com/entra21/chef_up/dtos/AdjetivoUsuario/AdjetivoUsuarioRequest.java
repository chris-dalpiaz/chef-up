package com.entra21.chef_up.dtos.AdjetivoUsuario;

public class AdjetivoUsuarioRequest {
    /// Relação muitos-para-um com a entidade Adjetivo
    private Integer idAdjetivo;

    public Integer getIdAdjetivo() {
        return idAdjetivo;
    }

    public void setIdAdjetivo(Integer idAdjetivo) {
        this.idAdjetivo = idAdjetivo;
    }
}
