package com.entra21.chef_up.dtos.IngredienteReceita;

public class IngredienteReceitaRequest {

    /// Ingrediente associado à receita
    private Integer idIngrediente;

    /// Unidade de medida da quantidade (ex: gramas, ml, unidades)
    private String unidadeMedida;

    /// Quantidade do ingrediente na receita
    private Double quantidade;

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public Double getQuantidade() {
        return quantidade;
    }
}
