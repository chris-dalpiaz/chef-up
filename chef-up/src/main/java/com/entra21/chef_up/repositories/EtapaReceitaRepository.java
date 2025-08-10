package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.EtapaReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EtapaReceitaRepository extends JpaRepository<EtapaReceita, Integer> {
    @Query("SELECT COALESCE(MAX(e.ordem), 0) FROM EtapaReceita e WHERE e.receita.id = :idReceita")
    Integer findMaxOrdemByReceitaId(@Param("idReceita") Integer idReceita);

    List<EtapaReceita> findByReceitaId(Integer idReceita);
}
