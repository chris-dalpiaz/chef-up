package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.CodigoVerificacao.CodigoVerificacaoResponse;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.entities.CodigoVerificacao;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.CodigoVerificacaoRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CodigoVerificacaoService {

    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado com o id";

    private final CodigoVerificacaoRepository codigoVerificacaoRepository;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public CodigoVerificacaoService(CodigoVerificacaoRepository codigoVerificacaoRepository, UsuarioService usuarioService, UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.codigoVerificacaoRepository = codigoVerificacaoRepository;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public CodigoVerificacao findByUsuarioId(Integer idUsuario) {
        return codigoVerificacaoRepository.findByUsuarioId(idUsuario).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));
    }

    public CodigoVerificacaoResponse create(Integer codigo, Integer idUsuario) {
        CodigoVerificacao code = new CodigoVerificacao();
        Optional<Usuario> user = usuarioRepository.findById(idUsuario);

        code.setCodigo(codigo);
        code.setUsuario(user.get());

        CodigoVerificacao saved = codigoVerificacaoRepository.save(code);
        return toResponse(saved);
    }

    public CodigoVerificacaoResponse toResponse(CodigoVerificacao codigoVerificacao) {
        CodigoVerificacaoResponse response = modelMapper.map(codigoVerificacao, CodigoVerificacaoResponse.class);

        return response;
    }

    public CodigoVerificacaoResponse getByUsuarioId(Integer id) {
        CodigoVerificacao code = codigoVerificacaoRepository.findByUsuarioId(id).get();
        return toResponse(code);
    }

    private CodigoVerificacao findEntityById(Integer id) {
        return codigoVerificacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND)
                );
    }
}
