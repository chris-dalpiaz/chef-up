package com.entra21.chef_up.dtos.IngredienteEtapaReceita;

import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaResponse;

public class IngredienteEtapaReceitaResponse {
    private IngredienteReceitaResponse ingredienteReceita;
    private EtapaReceitaResponse etapaReceita;

    public IngredienteReceitaResponse getIngredienteReceita() {
        return ingredienteReceita;
    }

    public void setIngredienteReceita(IngredienteReceitaResponse ingredienteReceita) {
        this.ingredienteReceita = ingredienteReceita;
    }

    public EtapaReceitaResponse getEtapaReceita() {
        return etapaReceita;
    }

    public void setEtapaReceita(EtapaReceitaResponse etapaReceita) {
        this.etapaReceita = etapaReceita;
    }
}
