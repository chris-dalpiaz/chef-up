package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Avatar.AvatarRequest;
import com.entra21.chef_up.dtos.Avatar.AvatarResponse;
import com.entra21.chef_up.entities.Avatar;
import com.entra21.chef_up.repositories.AvatarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações de CRUD para Avatar.
 */
@Service
public class AvatarService {

    private static final String ERROR_AVATAR_NOT_FOUND = "Avatar não encontrado";

    private final AvatarRepository avatarRepository;
    private final ModelMapper mapper;

    public AvatarService(AvatarRepository avatarRepository, ModelMapper mapper) {
        this.avatarRepository = avatarRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todos os avatares cadastrados.
     *
     * @return lista de AvatarResponse
     */
    public List<AvatarResponse> listAll() {
        return avatarRepository.findAll()
                .stream()
                .map(avatar -> mapper.map(avatar, AvatarResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca um avatar pelo seu ID.
     *
     * @param id identificador do avatar
     * @return DTO do avatar encontrado
     */
    public AvatarResponse getById(Integer id) {
        Avatar avatar = findByIdOrThrow(id);
        return mapper.map(avatar, AvatarResponse.class);
    }

    /**
     * Cria um novo avatar.
     *
     * @param request DTO com os dados do avatar
     * @return DTO do avatar criado
     */
    public AvatarResponse create(AvatarRequest request) {
        Avatar newAvatar = mapper.map(request, Avatar.class);
        Avatar saved = avatarRepository.save(newAvatar);
        return mapper.map(saved, AvatarResponse.class);
    }

    /**
     * Atualiza um avatar existente.
     *
     * @param id      identificador do avatar
     * @param request DTO com os dados atualizados
     * @return DTO do avatar atualizado
     */
    public AvatarResponse update(Integer id, AvatarRequest request) {
        Avatar existing = findByIdOrThrow(id);

        existing.setNome(request.getNome());
        existing.setImagemUrl(request.getImagemUrl());

        Avatar updated = avatarRepository.save(existing);
        return mapper.map(updated, AvatarResponse.class);
    }

    /**
     * Remove um avatar pelo ID.
     *
     * @param id identificador do avatar
     * @return DTO do avatar removido
     */
    public AvatarResponse delete(Integer id) {
        Avatar avatar = findByIdOrThrow(id);
        avatarRepository.deleteById(id);
        return mapper.map(avatar, AvatarResponse.class);
    }

    /**
     * Busca o avatar ou lança exceção 404.
     *
     * @param id identificador
     * @return entidade Avatar
     */
    private Avatar findByIdOrThrow(Integer id) {
        return avatarRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_AVATAR_NOT_FOUND));
    }
}