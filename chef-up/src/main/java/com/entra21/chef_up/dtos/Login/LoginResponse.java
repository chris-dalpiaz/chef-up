package com.entra21.chef_up.dtos.Login;

import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;

/** DTO para enviar a resposta do login com o token JWT */
public class LoginResponse {
    public String token;  /// Token JWT gerado após login bem-sucedido
    public UsuarioResponse usuario;

    /** Construtor que recebe o token */

    public LoginResponse(String token, UsuarioResponse usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}
