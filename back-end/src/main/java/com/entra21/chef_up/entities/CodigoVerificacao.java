package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class CodigoVerificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcodigo_verificacao;
    private Integer codigo;

    @OneToOne()
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Integer getIdcodigo_verificacao() {
        return idcodigo_verificacao;
    }

    public void setIdcodigo_verificacao(Integer idcodigo_verificacao) {
        this.idcodigo_verificacao = idcodigo_verificacao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
