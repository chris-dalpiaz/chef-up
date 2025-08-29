package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.CodigoVerificacao.CodigoVerificacaoResponse;
import com.entra21.chef_up.dtos.CodigoVerificacao.EmailRequest;
import com.entra21.chef_up.dtos.CodigoVerificacao.ValidacaoRequest;
import com.entra21.chef_up.dtos.CodigoVerificacao.ValidacaoResponse;
import com.entra21.chef_up.entities.CodigoVerificacao;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.services.CodigoVerificacaoService;
import com.entra21.chef_up.repositories.CodigoVerificacaoRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codigos")
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
    public List<CodigoVerificacaoResponse> listCodigos() {
        return codigoVerificacaoService.listAll();
    }

    @GetMapping("/{idUsuario}")
    public CodigoVerificacaoResponse getCodigoVerificacao(@PathVariable Integer idUsuario) {
        return codigoVerificacaoService.getByUsuarioId(idUsuario);
    }

    @PostMapping
    public CodigoVerificacaoResponse gerarCodigo(@RequestBody EmailRequest request) {

        Integer codigo = (int) (Math.random() * 9999);

        return codigoVerificacaoService.create(codigo, request.getEmail());
    }

    @DeleteMapping
    public List<CodigoVerificacao> deletarCodigos() {
        List<CodigoVerificacao> list = codigoVerificacaoRepository.findAll();
        codigoVerificacaoRepository.deleteAll();

        return list;
    }

    @PostMapping("/validacao")
    public ValidacaoResponse validacaoCodigo(@RequestBody ValidacaoRequest validacaoRequest) {
        Usuario user = usuarioRepository.findByEmail(validacaoRequest.getEmail()).get();
        CodigoVerificacao codeVerification = codigoVerificacaoRepository.findByUsuarioId(user.getId()).get();

        String codeConverted = validacaoRequest.getDigitCode();
        String codeDTB = String.format("%04d", codeVerification.getCodigo());

        ValidacaoResponse validacaoResponse = new ValidacaoResponse();

        System.out.println("Código escrito: " +codeConverted);
        System.out.println("Código da banco: "+codeVerification.getCodigo());

        if (codeConverted.equals(codeDTB)) {
            validacaoResponse.setCodigoValido(true);
            return validacaoResponse;
        } else {
            validacaoResponse.setCodigoValido(false);
            return validacaoResponse;
        }
    }
}
