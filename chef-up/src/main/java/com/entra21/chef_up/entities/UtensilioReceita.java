package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class UtensilioReceita {
    /// ID único da relação entre utensílio e receita
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Referência ao utensílio usado na receita
    @ManyToOne()
    @JoinColumn(name = "utensilios_id")
    private Utensilio utensilio;

    /// Referência à receita que usa o utensílio
    @ManyToOne()
    @JoinColumn(name = "receitas_id")
    private Receita receita;

    /** Retorna o ID da relação */
    public Integer getId() {
        return id;
    }

    /** Define o ID da relação */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Retorna o utensílio associado */
    public Utensilio getUtensilio() {
        return utensilio;
    }

    /** Define o utensílio associado */
    public void setUtensilio(Utensilio utensilio) {
        this.utensilio = utensilio;
    }

    /** Retorna a receita associada */
    public Receita getReceita() {
        return receita;
    }

    /** Define a receita associada */
    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
