package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.CodigoVerificacao;
import com.entra21.chef_up.repositories.UsuarioRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{idUsuario}/codigo")
public class CodigoVerificacaoController {

    private final UsuarioRepository usuarioRepository;
    private final CodigoVerificacaoService codigoVerificacaoService;
    private final CodigoVerificacaoRepository codigoVerificacaoRepository;

    public CodigoVerificacaoController(UsuarioRepository usuarioRepository, CodigoVerificacaoService codigoVerificacaoService, CodigoVerificacaoRepository codigoVerificacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.codigoVerificacaoService = codigoVerificacaoService;
        this.codigoVerificacaoRepository = codigoVerificacaoRepository;
    }

    public CodigoVerificacao getCodigoVerificao(@PathVariable Integer idUsuario) {
        return codigoVerificacaoService.findByIdUsuario(idUsuario);
    }
}
