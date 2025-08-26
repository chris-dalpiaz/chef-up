package com.entra21.chef_up.dtos.CodigoVerificacao;

import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;

public class CodigoVerificacaoResponse {

    private Integer codigo;

    private UsuarioResponse usuario;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }
}
