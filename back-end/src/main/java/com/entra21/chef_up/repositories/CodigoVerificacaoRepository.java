package com.entra21.chef_up.repositories;

import com.entra21.chef_up.entities.CodigoVerificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoVerificacaoRepository extends JpaRepository<CodigoVerificacao, Integer> {
    Optional<CodigoVerificacao> findByUsuarioId(Integer idUsuario);
}
