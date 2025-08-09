package com.entra21.chef_up.repository;

import com.entra21.chef_up.entities.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Integer> {
}
