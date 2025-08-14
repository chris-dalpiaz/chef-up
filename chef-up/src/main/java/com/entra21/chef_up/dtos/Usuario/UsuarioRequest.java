package com.entra21.chef_up.dtos.Usuario;

public class UsuarioRequest {

    private String nome;

    private String email;

    private String senhaHash;

    private Integer idPronome;

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
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
