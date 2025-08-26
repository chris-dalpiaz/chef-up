package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.CodigoVerificacao.CodigoVerificacaoResponse;
import com.entra21.chef_up.services.CodigoVerificacaoService;
import com.entra21.chef_up.repositories.CodigoVerificacaoRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public CodigoVerificacaoResponse getCodigoVerificao(@PathVariable Integer idUsuario) {
        return codigoVerificacaoService.getByUsuarioId(idUsuario);
    }

    @PostMapping
    public CodigoVerificacaoResponse gerarCodigo(@PathVariable Integer idUsuario) {

        int codigo = (int) (Math.random() * 9999);

        return codigoVerificacaoService.create(codigo, idUsuario);
    }
}
