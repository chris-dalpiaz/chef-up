package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.IngredienteReceita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredienteReceitaRepository extends JpaRepository<IngredienteReceita, Integer> {
    List<IngredienteReceita> findByReceitaId(Integer idReceita);

    List<IngredienteReceita> removeByReceitaId(Integer idReceita);
}
