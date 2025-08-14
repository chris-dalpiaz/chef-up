package com.entra21.chef_up.dtos.IngredienteReceita;

import com.entra21.chef_up.dtos.Ingrediente.IngredienteResponse;

public class IngredienteReceitaResponse {
    /// Ingrediente associado à receita
    private IngredienteResponse ingrediente;

    /// Unidade de medida da quantidade (ex: gramas, ml, unidades)
    private String unidadeMedida;

    /// Quantidade do ingrediente na receita
    private Double quantidade;

    public IngredienteResponse getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteResponse ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
