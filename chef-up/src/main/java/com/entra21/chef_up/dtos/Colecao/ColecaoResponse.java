package com.entra21.chef_up.dtos.Colecao;

import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;

public class ColecaoResponse {

    private Integer id;

    private String nome;

    private UsuarioResponse usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }
}
