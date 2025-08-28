package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.IngredienteEtapaReceita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredienteEtapaReceitaRepository extends JpaRepository<IngredienteEtapaReceita, Integer> {

    List<IngredienteEtapaReceita> findByEtapaReceitaId(Integer etapaId);
}

