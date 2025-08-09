package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String email;
    private String senhaHash;
    private LocalDateTime dataCadastro;

    @ManyToOne()
    @JoinColumn(name = "pronomes_id")
    private Pronome pronome;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Pronome getPronome() {
        return pronome;
    }

    public void setPronome(Pronome pronome) {
        this.pronome = pronome;
    }

    public String getPassword() {
    }
}
