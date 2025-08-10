package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
