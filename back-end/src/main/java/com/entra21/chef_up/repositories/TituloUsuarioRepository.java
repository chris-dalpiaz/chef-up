package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.TituloUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TituloUsuarioRepository extends JpaRepository<TituloUsuario, Integer> {
    List<TituloUsuario> findByUsuarioId(Integer idUsuario);
    boolean existsByUsuarioIdAndTituloId(Integer usuarioId, Integer tituloId);
}
