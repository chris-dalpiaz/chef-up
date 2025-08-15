package com.entra21.chef_up.dtos.IngredienteUsuario;

import com.entra21.chef_up.dtos.Ingrediente.IngredienteResponse;

import java.time.LocalDateTime;

public class IngredienteUsuarioResponse {

    private IngredienteResponse ingrediente;

    private LocalDateTime dataAdicionada;

    public LocalDateTime getDataAdicionada() {
        return dataAdicionada;
    }

    public void setDataAdicionada(LocalDateTime dataAdicionada) {
        this.dataAdicionada = dataAdicionada;
    }

    public IngredienteResponse getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteResponse ingrediente) {
        this.ingrediente = ingrediente;
    }
}
