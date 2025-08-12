package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class EtapaReceita {
    /// Identificador único da etapa da receita
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Ordem da etapa na receita (ex: 1, 2, 3...)
    private Integer ordem;

    /// Conteúdo/instruções da etapa
    private String conteudo;

    /// Receita à qual esta etapa pertence (muitos-para-um)
    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receita receita;

    /**
     * Retorna o ID da etapa.
     *
     * @return id da etapa
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da etapa.
     *
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna a ordem da etapa na receita.
     *
     * @return ordem da etapa
     */
    public Integer getOrdem() {
        return ordem;
    }

    /**
     * Define a ordem da etapa na receita.
     *
     * @param ordem nova ordem da etapa
     */
    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    /**
     * Retorna o conteúdo/instruções da etapa.
     *
     * @return conteúdo da etapa
     */
    public String getConteudo() {
        return conteudo;
    }

    /**
     * Define o conteúdo/instruções da etapa.
     *
     * @param conteudo novo conteúdo da etapa
     */
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    /**
     * Retorna a receita à qual esta etapa pertence.
     *
     * @return receita da etapa
     */
    public Receita getReceita() {
        return receita;
    }

    /**
     * Define a receita à qual esta etapa pertence.
     *
     * @param receita receita associada à etapa
     */
    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
