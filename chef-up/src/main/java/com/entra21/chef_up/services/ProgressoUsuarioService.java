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

/**
 * Serviço responsável por gerenciar a entidade ProgressoUsuario.
 */
@Service
public class ProgressoUsuarioService {

    private static final String ERROR_PROGRESS_NOT_FOUND = "Progresso não encontrado";

    private final ProgressoUsuarioRepository progressRepository;
    private final ModelMapper mapper;

    public ProgressoUsuarioService(UsuarioService usuarioService,
                                   ProgressoUsuarioRepository progressRepository,
                                   ModelMapper mapper) {
        this.progressRepository = progressRepository;
        this.mapper = mapper;
    }

    /**
     * Recupera os dados de progresso de um determinado usuário.
     *
     * @param userId identificador do usuário
     * @return DTO com os dados de progresso
     */
    public ProgressoUsuarioResponse getByUserId(Integer userId) {
        ProgressoUsuario progress = findByUserIdOrThrow(userId);
        return mapper.map(progress, ProgressoUsuarioResponse.class);
    }

    /**
     * Atualiza os dados de progresso de um determinado usuário.
     *
     * @param userId  identificador do usuário
     * @param request DTO com os dados atualizados de progresso
     * @return DTO com os dados de progresso atualizados
     */
    public ProgressoUsuarioResponse update(Integer userId, ProgressoUsuarioRequest request) {
        ProgressoUsuario progress = findByUserIdOrThrow(userId);

        progress.setXp(request.getXp());
        progress.setNivel(request.getNivel());
        progress.setAtualizadoEm(LocalDateTime.now());

        ProgressoUsuario updated = progressRepository.save(progress);
        return mapper.map(updated, ProgressoUsuarioResponse.class);
    }

    /**
     * Busca o progresso pelo ID do usuário ou lança exceção 404.
     *
     * @param userId identificador do usuário
     * @return entidade ProgressoUsuario
     */
    private ProgressoUsuario findByUserIdOrThrow(Integer userId) {
        return progressRepository.findByUsuarioId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_PROGRESS_NOT_FOUND));
    }
}
