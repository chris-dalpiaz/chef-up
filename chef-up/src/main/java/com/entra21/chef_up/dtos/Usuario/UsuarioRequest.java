package com.entra21.chef_up.dtos.Usuario;

public class UsuarioRequest {
    /// Nome completo do usuário
    private String nome;

    /// Email do usuário (único para login)
    private String email;

    /// Hash da senha do usuário (para segurança)
    private String senhaHash;

    /// Pronome preferido do usuário (relação ManyToOne)
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
