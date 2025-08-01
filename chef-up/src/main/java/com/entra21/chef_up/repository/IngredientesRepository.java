package com.entra21.chef_up.repository;

import com.entra21.chef_up.entities.Ingredientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientesRepository extends JpaRepository<Ingredientes, Integer> {
}
