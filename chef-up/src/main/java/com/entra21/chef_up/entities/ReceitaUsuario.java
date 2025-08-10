package com.entra21.chef_up.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class ReceitaUsuario {
    /// ID único da associação entre usuário e receita
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Usuário que realizou a receita (muitos para um)
    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    /// Receita que foi realizada (muitos para um)
    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receita receita;

    /// Data e hora em que o usuário concluiu a receita
    private LocalDateTime dataConclusao;

    /// URL ou caminho da foto do prato feito pelo usuário
    private String fotoPrato;

    /// Pontuação dada ao prato feito pelo usuário
    private Integer pontuacaoPrato;

    /** Retorna o ID da associação */
    public Integer getId() {
        return id;
    }

    /** Define o ID da associação */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Retorna o usuário que realizou a receita */
    public Usuario getUsuario() {
        return usuario;
    }

    /** Define o usuário que realizou a receita */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** Retorna a receita realizada */
    public Receita getReceita() {
        return receita;
    }

    /** Define a receita realizada */
    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    /** Retorna a data de conclusão da receita */
    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    /** Define a data de conclusão da receita */
    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    /** Retorna a foto do prato */
    public String getFotoPrato() {
        return fotoPrato;
    }

    /** Define a foto do prato */
    public void setFotoPrato(String fotoPrato) {
        this.fotoPrato = fotoPrato;
    }

    /** Retorna a pontuação do prato */
    public Integer getPontuacaoPrato() {
        return pontuacaoPrato;
    }

    /** Define a pontuação do prato */
    public void setPontuacaoPrato(Integer pontuacaoPrato) {
        this.pontuacaoPrato = pontuacaoPrato;
    }
}
