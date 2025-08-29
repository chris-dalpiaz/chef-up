package com.entra21.chef_up.dtos.TituloUsuario;


import com.entra21.chef_up.dtos.Titulo.TituloResponse;

import java.time.LocalDateTime;

public class TituloUsuarioResponse {

    private Integer id;

    private TituloResponse titulo;

    private LocalDateTime desbloqueadoEm;

    private Boolean estaAtivo;

    public TituloUsuarioResponse(String s) {
    }

    // Construtor vazio (necessário para frameworks)
    public TituloUsuarioResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getEstaAtivo() {
        return estaAtivo;
    }

    public void setEstaAtivo(Boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
    }

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
