package com.entra21.chef_up.dtos.Login;

/** DTO para enviar a resposta do login com o token JWT */
public class LoginResponse {
    public String token;  /// Token JWT gerado após login bem-sucedido

    /** Construtor que recebe o token */
    public LoginResponse(String token) {
        this.token = token;
    }
}
