package com.entra21.chef_up.services;

import com.entra21.chef_up.entities.CodigoVerificacao;
import com.entra21.chef_up.repositories.CodigoVerificacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CodigoVerificacaoService {

    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado com o id";

    private final CodigoVerificacaoRepository codigoVerificacaoRepository;
    private final UsuarioService usuarioService;

    public CodigoVerificacaoService(CodigoVerificacaoRepository codigoVerificacaoRepository, UsuarioService usuarioService) {
        this.codigoVerificacaoRepository = codigoVerificacaoRepository;
        this.usuarioService = usuarioService;
    }

    public CodigoVerificacao findByUsuarioId(Integer idUsuario) {
        return codigoVerificacaoRepository.findByUsuarioId(idUsuario).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));
    }
}
