package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Usuario {
    /// ID único do usuário
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Nome completo do usuário
    private String nome;

    /// Email do usuário (único para login)
    private String email;

    /// Hash da senha do usuário (para segurança)
    private String senhaHash;

    /// Data e hora do cadastro do usuário
    private LocalDateTime dataCadastro;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private ProgressoUsuario progressoUsuario;

    /// Pronome preferido do usuário (relação ManyToOne)
    @ManyToOne()
    @JoinColumn(name = "pronomes_id")
    private Pronome pronome;

    public ProgressoUsuario getProgressoUsuario() {
        return progressoUsuario;
    }

    public void setProgressoUsuario(ProgressoUsuario progressoUsuario) {
        this.progressoUsuario = progressoUsuario;
    }

    /** Retorna o ID do usuário */
    public Integer getId() {
        return id;
    }

    /** Define o ID do usuário */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Retorna o nome do usuário */
    public String getNome() {
        return nome;
    }

    /** Define o nome do usuário */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** Retorna o email do usuário */
    public String getEmail() {
        return email;
    }

    /** Define o email do usuário */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Retorna o hash da senha do usuário */
    public String getSenhaHash() {
        return senhaHash;
    }

    /** Define o hash da senha do usuário */
    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    /** Retorna a data de cadastro do usuário */
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    /** Define a data de cadastro do usuário */
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /** Retorna o pronome preferido do usuário */
    public Pronome getPronome() {
        return pronome;
    }

    /** Define o pronome preferido do usuário */
    public void setPronome(Pronome pronome) {
        this.pronome = pronome;
    }
}
