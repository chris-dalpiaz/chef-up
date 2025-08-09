package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ReceitaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receita receita;

    private Date dataCricacao;
    private String fotoPrato;
    private Integer pontuacaoPrato;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Date getDataCricacao() {
        return dataCricacao;
    }

    public void setDataCricacao(Date dataCricacao) {
        this.dataCricacao = dataCricacao;
    }

    public String getFotoPrato() {
        return fotoPrato;
    }

    public void setFotoPrato(String fotoPrato) {
        this.fotoPrato = fotoPrato;
    }

    public Integer getPontuacaoPrato() {
        return pontuacaoPrato;
    }

    public void setPontuacaoPrato(Integer pontuacaoPrato) {
        this.pontuacaoPrato = pontuacaoPrato;
    }
}
