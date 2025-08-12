package com.entra21.chef_up.services;


import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioRequest;
import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioResponse;
import com.entra21.chef_up.entities.ProgressoUsuario;
import com.entra21.chef_up.repositories.ProgressoUsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgressoUsuarioService {

    private final ProgressoUsuarioRepository progressoUsuarioRepository;
    private final ModelMapper modelMapper;

    public ProgressoUsuarioService(UsuarioService usuarioService, ProgressoUsuarioRepository progressoUsuario, ModelMapper modelMapper) {
        this.progressoUsuarioRepository = progressoUsuario;
        this.modelMapper = modelMapper;
    }

    public ProgressoUsuarioResponse buscar(Integer id) {
        ProgressoUsuario progressoUsuario = progressoUsuarioRepository.findByUsuarioId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progresso não encontrado"));

        return modelMapper.map(progressoUsuario, ProgressoUsuarioResponse.class);
    }

    public ProgressoUsuarioResponse alterar(Integer id, ProgressoUsuarioRequest request) {
        /// Busca o adjetivo pelo ID ou lança erro 404
        ProgressoUsuario alterar = progressoUsuarioRepository.findByUsuarioId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progresso não encontrado"));

        /// Atualiza o nome com os dados do request
        alterar.setXp(request.getXp());
        alterar.setNivel(request.getNivel());
        alterar.setAtualizadoEm(LocalDateTime.now());

        /// Salva a alteração no banco
        ProgressoUsuario salvo = progressoUsuarioRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, ProgressoUsuarioResponse.class);
    }
}
