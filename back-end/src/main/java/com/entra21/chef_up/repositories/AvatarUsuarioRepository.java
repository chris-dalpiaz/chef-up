package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.AvatarUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvatarUsuarioRepository extends JpaRepository<AvatarUsuario, Integer> {
    List<AvatarUsuario> findByUsuarioId(Integer idUsuario);

    Optional<AvatarUsuario> findByUsuarioIdAndAvatarId(Integer usuarioId, Integer avatarId);
}
