package com.entra21.chef_up.dtos.Usuario;

import com.entra21.chef_up.dtos.Pronome.PronomeResponse;

public class UsuarioResponse {
    /// Nome completo do usuário
    private String nome;

    /// Email do usuário (único para login)
    private String email;

    /// Pronome do usuário (relação ManyToOne)
    private PronomeResponse pronome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PronomeResponse getPronome() {
        return pronome;
    }

    public void setPronome(PronomeResponse pronome) {
        this.pronome = pronome;
    }
}
