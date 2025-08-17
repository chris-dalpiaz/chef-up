package com.entra21.chef_up.dtos.ReceitaColecao;

import com.entra21.chef_up.dtos.Receita.ReceitaResponse;

public class ReceitaColecaoResponse {

    private Integer id;

    private ReceitaResponse receita;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public ReceitaResponse getReceita() {
        return receita;
    }

    public void setReceita(ReceitaResponse receita) {
        this.receita = receita;
    }
}
