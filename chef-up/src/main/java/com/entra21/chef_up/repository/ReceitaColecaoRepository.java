package com.entra21.chef_up.repository;

import com.entra21.chef_up.entities.ReceitaColecao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceitaColecaoRepository extends JpaRepository<ReceitaColecao, Integer> {

    List<ReceitaColecao> findByColecaoId(Integer idColecao);
}
