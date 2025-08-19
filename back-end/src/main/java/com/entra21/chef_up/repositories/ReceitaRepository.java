package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceitaRepository extends JpaRepository<Receita, Integer> {
}
