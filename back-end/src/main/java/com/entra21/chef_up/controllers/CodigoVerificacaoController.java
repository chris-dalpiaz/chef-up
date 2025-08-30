package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.CodigoVerificacao.*;
import com.entra21.chef_up.entities.CodigoVerificacao;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.services.CodigoVerificacaoService;
import com.entra21.chef_up.repositories.CodigoVerificacaoRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codigos")
public class CodigoVerificacaoController {

    private final UsuarioRepository usuarioRepository;
    private final CodigoVerificacaoService codigoVerificacaoService;
    private final CodigoVerificacaoRepository codigoVerificacaoRepository;
    private final PasswordEncoder passwordEncoder;

    public CodigoVerificacaoController(UsuarioRepository usuarioRepository, CodigoVerificacaoService codigoVerificacaoService, CodigoVerificacaoRepository codigoVerificacaoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.codigoVerificacaoService = codigoVerificacaoService;
        this.codigoVerificacaoRepository = codigoVerificacaoRepository;
        this.passwordEncoder = passwordEncoder;
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

    @PutMapping
    public ResetSenhaResponse redefinirSenha(@RequestBody ResetSenhaRequest resetSenhaRequest) {
        Usuario user = usuarioRepository.findByEmail(resetSenhaRequest.getEmail()).get();

        String antigaSenha = user.getSenha();
        String novaSenha = passwordEncoder.encode(resetSenhaRequest.getNovaSenha());

        user.setSenha(novaSenha);

        String senhaAtual = passwordEncoder.encode(user.getSenha());

        ResetSenhaResponse newResponse = new ResetSenhaResponse();

        System.out.println(user.getEmail());
        System.out.println(antigaSenha);
        System.out.println(user.getSenha());

        boolean sucesso = senhaAtual.equals(antigaSenha);

        if (sucesso) {
            newResponse.setSenhaAlterada(false);
            return newResponse;
        } else {
            newResponse.setSenhaAlterada(true);
            return newResponse;
        }
    }
}
