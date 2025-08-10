package com.entra21.chef_up.dtos.login;

/** DTO para receber dados de login */
public class LoginRequest {
    public String email;  /// Email do usuário
    public String senha;  /// Senha do usuário

    /** Getter para email */
    public String getEmail() {
        return email;
    }

    /** Getter para senha */
    public String getSenha() {
        return senha;
    }
}
