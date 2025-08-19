package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.IngredienteReceita;
import com.entra21.chef_up.entities.UtensilioReceita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtensilioReceitaRepository extends JpaRepository<UtensilioReceita, Integer> {
    List<UtensilioReceita> findByReceitaId(Integer idReceita);

    List<UtensilioReceita> removeByReceitaId(Integer idReceita);
}
