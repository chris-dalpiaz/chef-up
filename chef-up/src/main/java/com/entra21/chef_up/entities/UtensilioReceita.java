package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class UtensilioReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "utensilios_id")
    private Utensilio utensilio;

    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receita receita;

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utensilio getUtensilio() {
        return utensilio;
    }

    public void setUtensilio(Utensilio utensilio) {
        this.utensilio = utensilio;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
