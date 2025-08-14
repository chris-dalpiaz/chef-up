package com.entra21.chef_up.dtos.IngredienteReceita;

public class IngredienteReceitaRequest {

    private Integer idIngrediente;

    private String unidadeMedida;

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
