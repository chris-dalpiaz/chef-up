package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class UtensiliosReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "utensilios_id")
    private Utensilios utensilio;

    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receitas receita;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utensilios getUtensilio() {
        return utensilio;
    }

    public void setUtensilio(Utensilios utensilio) {
        this.utensilio = utensilio;
    }

    public Receitas getReceita() {
        return receita;
    }

    public void setReceita(Receitas receita) {
        this.receita = receita;
    }
}
