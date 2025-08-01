package com.entra21.chef_up.repository;

import com.entra21.chef_up.entities.Receitas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitasRepository extends JpaRepository<Receitas, Integer> {
}
