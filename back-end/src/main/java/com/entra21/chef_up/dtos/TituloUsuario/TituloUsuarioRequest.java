package com.entra21.chef_up.dtos.TituloUsuario;

public class TituloUsuarioRequest {

    private Integer idTitulo;

    private Boolean estaAtivo;

    public Boolean getEstaAtivo() {
        return estaAtivo;
    }

    public void setEstaAtivo(Boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
    }
    public Integer getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(Integer idTitulo) {
        this.idTitulo = idTitulo;
    }
}
