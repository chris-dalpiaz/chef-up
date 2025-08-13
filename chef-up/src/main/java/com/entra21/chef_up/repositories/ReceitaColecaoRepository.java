package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.IngredienteReceita;
import com.entra21.chef_up.entities.ReceitaColecao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceitaColecaoRepository extends JpaRepository<ReceitaColecao, Integer> {

    List<ReceitaColecao> findByColecaoId(Integer idColecao);

    List<ReceitaColecao> removeByReceitaId(Integer idReceita);

    List<ReceitaColecao> removeByColecaoId(Integer id);
}
