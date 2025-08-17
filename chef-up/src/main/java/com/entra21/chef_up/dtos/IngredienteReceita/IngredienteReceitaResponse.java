package com.entra21.chef_up.dtos.IngredienteReceita;

import com.entra21.chef_up.dtos.Ingrediente.IngredienteResponse;

public class IngredienteReceitaResponse {

    private Integer id;

    private IngredienteResponse ingrediente;

    private String unidadeMedida;

    private Double quantidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
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
