package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class AdjetivoUsuario {
    /// Identificador único da associação (chave primária)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Relação muitos-para-um com a entidade Adjetivo
    @ManyToOne()
    @JoinColumn(name = "adjetivos_id")
    private Adjetivo adjetivo;

    /// Relação muitos-para-um com a entidade Usuario
    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    /**
     * Retorna o ID da associação AdjetivoUsuario.
     * @return id da associação
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da associação AdjetivoUsuario.
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o adjetivo associado ao usuário.
     * @return objeto Adjetivo
     */
    public Adjetivo getAdjetivo() {
        return adjetivo;
    }

    /**
     * Define o adjetivo associado ao usuário.
     * @param adjetivo objeto Adjetivo para associar
     */
    public void setAdjetivo(Adjetivo adjetivo) {
        this.adjetivo = adjetivo;
    }

    /**
     * Retorna o usuário associado ao adjetivo.
     * @return objeto Usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário associado ao adjetivo.
     * @param usuario objeto Usuario para associar
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
