package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredienteUsuarioRepository extends JpaRepository<com.entra21.chef_up.entities.IngredienteUsuario, Integer> {
    List<com.entra21.chef_up.entities.IngredienteUsuario> findByUsuarioId(Integer idUsuario);
    boolean existsByUsuarioAndIngrediente(Usuario usuario, Ingrediente ingrediente);
}
