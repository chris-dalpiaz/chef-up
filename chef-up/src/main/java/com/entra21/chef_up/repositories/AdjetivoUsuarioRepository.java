package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.AdjetivoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjetivoUsuarioRepository extends JpaRepository<AdjetivoUsuario, Integer> {

    List<AdjetivoUsuario> findByUsuarioId(Integer idUsuario);

}
