package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.ProgressoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgressoUsuarioRepository extends JpaRepository<ProgressoUsuario, Integer> {
    Optional<ProgressoUsuario> findByUsuarioId(Integer idUsuario);
}
