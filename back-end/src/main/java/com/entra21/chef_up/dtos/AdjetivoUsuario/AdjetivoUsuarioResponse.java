package com.entra21.chef_up.dtos.AdjetivoUsuario;

import com.entra21.chef_up.dtos.Adjetivo.AdjetivoResponse;

public class AdjetivoUsuarioResponse {

    private Integer id;

    private AdjetivoResponse adjetivo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdjetivoResponse getAdjetivo() {
        return adjetivo;
    }

    public void setAdjetivo(AdjetivoResponse adjetivo) {
        this.adjetivo = adjetivo;
    }
}
