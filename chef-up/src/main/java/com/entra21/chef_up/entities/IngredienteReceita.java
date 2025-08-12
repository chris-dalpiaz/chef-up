package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class IngredienteReceita {
    /// Identificador único da relação ingrediente-receita
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Ingrediente associado à receita
    @ManyToOne()
    @JoinColumn(name = "ingredientes_id")
    private Ingrediente ingrediente;

    /// Receita que utiliza o ingrediente
    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receita receita;

    /// Unidade de medida da quantidade (ex: gramas, ml, unidades)
    private String unidadeMedida;

    /// Quantidade do ingrediente na receita
    private Double quantidade;

    /**
     * Retorna o ID da relação ingrediente-receita.
     *
     * @return id da relação
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID da relação ingrediente-receita.
     *
     * @param id novo valor para o ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retorna o ingrediente associado.
     *
     * @return ingrediente
     */
    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    /**
     * Define o ingrediente associado.
     *
     * @param ingrediente novo ingrediente
     */
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    /**
     * Retorna a receita associada.
     *
     * @return receita
     */
    public Receita getReceita() {
        return receita;
    }

    /**
     * Define a receita associada.
     *
     * @param receita nova receita
     */
    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    /**
     * Retorna a unidade de medida da quantidade.
     *
     * @return unidade de medida
     */
    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    /**
     * Define a unidade de medida da quantidade.
     *
     * @param unidadeMedida nova unidade
     */
    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    /**
     * Retorna a quantidade do ingrediente na receita.
     *
     * @return quantidade
     */
    public Double getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do ingrediente na receita.
     *
     * @param quantidade nova quantidade
     */
    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
