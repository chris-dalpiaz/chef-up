package com.entra21.chef_up.dtos.IngredienteReceita;

import com.entra21.chef_up.dtos.Ingrediente.IngredienteResponse;

public class IngredienteReceitaResponse {

    private IngredienteResponse ingrediente;

    private String unidadeMedida;

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
