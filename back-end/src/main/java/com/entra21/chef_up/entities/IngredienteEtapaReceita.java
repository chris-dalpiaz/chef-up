package com.entra21.chef_up.entities;

import jakarta.persistence.*;

@Entity
public class IngredienteEtapaReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "ingrediente_receita_id")
    private IngredienteReceita ingredienteReceita;

    @ManyToOne()
    @JoinColumn(name = "etapa_receita_id")
    private EtapaReceita etapaReceita;

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

    public IngredienteReceita getIngredienteReceita() {
        return ingredienteReceita;
    }

    public void setIngredienteReceita(IngredienteReceita ingredienteReceita) {
        this.ingredienteReceita = ingredienteReceita;
    }

    public EtapaReceita getEtapaReceita() {
        return etapaReceita;
    }

    public void setEtapaReceita(EtapaReceita etapaReceita) {
        this.etapaReceita = etapaReceita;
    }
}
