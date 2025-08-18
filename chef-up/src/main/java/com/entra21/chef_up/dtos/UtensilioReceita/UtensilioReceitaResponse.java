package com.entra21.chef_up.dtos.UtensilioReceita;

import com.entra21.chef_up.dtos.Utensilio.UtensilioResponse;

public class UtensilioReceitaResponse {

    private Integer id;

    private UtensilioResponse utensilio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public UtensilioResponse getUtensilio() {
        return utensilio;
    }

    public void setUtensilio(UtensilioResponse utensilio) {
        this.utensilio = utensilio;
    }
}
