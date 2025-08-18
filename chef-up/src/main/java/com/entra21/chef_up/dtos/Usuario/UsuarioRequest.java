package com.entra21.chef_up.dtos.Usuario;

public class UsuarioRequest {

    private String nome;

    private String email;

    private String senha;

    private Integer idPronome;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

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

    public Integer getIdPronome() {
        return idPronome;
    }

    public void setIdPronome(Integer idPronome) {
        this.idPronome = idPronome;
    }


}
