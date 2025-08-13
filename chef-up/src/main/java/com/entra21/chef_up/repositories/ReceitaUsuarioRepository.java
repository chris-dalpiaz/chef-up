package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.IngredienteReceita;
import com.entra21.chef_up.entities.ReceitaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceitaUsuarioRepository extends JpaRepository<ReceitaUsuario, Integer> {
    List<ReceitaUsuario> findByUsuarioId(Integer idUsuario);

    List<ReceitaUsuario> removeByReceitaId(Integer idReceita);
}
