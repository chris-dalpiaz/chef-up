package com.entra21.chef_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredienteUsuarioRepository extends JpaRepository<com.entra21.chef_up.entities.IngredienteUsuario, Integer> {
    List<com.entra21.chef_up.entities.IngredienteUsuario> findByUsuarioId(Integer idUsuario);
}
