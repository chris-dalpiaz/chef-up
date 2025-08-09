package com.entra21.chef_up.repository;

import com.entra21.chef_up.entities.AvatarUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvatarUsuarioRepository extends JpaRepository<AvatarUsuario, Integer> {
    List<AvatarUsuario> findByUsuarioId(Integer idUsuario);
}
