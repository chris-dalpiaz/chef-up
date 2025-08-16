package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.AvatarUsuario.AvatarUsuarioRequest;
import com.entra21.chef_up.dtos.AvatarUsuario.AvatarUsuarioResponse;
import com.entra21.chef_up.entities.Avatar;
import com.entra21.chef_up.entities.AvatarUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.AvatarRepository;
import com.entra21.chef_up.repositories.AvatarUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar a relação entre Avatar e Usuario.
 */
@Service
public class AvatarUsuarioService {

    private static final String ERROR_ASSOCIATION_NOT_FOUND = "Associação não encontrada";
    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado";
    private static final String ERROR_AVATAR_NOT_FOUND = "Avatar não encontrado";
    private static final String ERROR_AVATAR_NOT_OWNED = "Avatar não pertence ao usuário";

    private final AvatarUsuarioRepository avatarUserRepository;
    private final AvatarRepository avatarRepository;
    private final UsuarioRepository userRepository;
    private final ModelMapper mapper;

    public AvatarUsuarioService(AvatarUsuarioRepository avatarUserRepository,
                                AvatarRepository avatarRepository,
                                UsuarioRepository userRepository,
                                ModelMapper mapper) {
        this.avatarUserRepository = avatarUserRepository;
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as associações entre avatar e usuário para um determinado usuário.
     *
     * @param userId identificador do usuário
     * @return lista de AvatarUsuarioResponse
     */
    public List<AvatarUsuarioResponse> listByUser(Integer userId) {
        return avatarUserRepository.findByUsuarioId(userId)
                .stream()
                .map(association -> mapper.map(association, AvatarUsuarioResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma associação específica entre avatar e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação encontrada
     */
    public AvatarUsuarioResponse getById(Integer userId, Integer associationId) {
        AvatarUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_AVATAR_NOT_OWNED);
        }

        return mapper.map(association, AvatarUsuarioResponse.class);
    }

    /**
     * Cria uma nova associação entre avatar e usuário.
     *
     * @param userId  identificador do usuário
     * @param request DTO com o ID do avatar
     * @return DTO da associação criada
     */
    public AvatarUsuarioResponse create(Integer userId, AvatarUsuarioRequest request) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));

        Avatar avatar = avatarRepository.findById(request.getIdAvatar())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_AVATAR_NOT_FOUND));

        AvatarUsuario newAssociation = new AvatarUsuario();
        newAssociation.setUsuario(user);
        newAssociation.setAvatar(avatar);

        AvatarUsuario saved = avatarUserRepository.save(newAssociation);
        return mapper.map(saved, AvatarUsuarioResponse.class);
    }

    /**
     * Atualiza uma associação existente entre avatar e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @param request       DTO com o novo ID do avatar
     * @return DTO da associação atualizada
     */
    public AvatarUsuarioResponse update(Integer userId, Integer associationId, AvatarUsuarioRequest request) {
        AvatarUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_AVATAR_NOT_OWNED);
        }

        if (request.getIdAvatar() != null && !request.getIdAvatar().equals(association.getAvatar().getId())) {
            Avatar newAvatar = avatarRepository.findById(request.getIdAvatar())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo avatar não encontrado"));
            association.setAvatar(newAvatar);
        }

        AvatarUsuario updated = avatarUserRepository.save(association);
        return mapper.map(updated, AvatarUsuarioResponse.class);
    }

    /**
     * Remove uma associação entre avatar e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação removida
     */
    @Transactional
    public AvatarUsuarioResponse delete(Integer userId, Integer associationId) {
        AvatarUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_AVATAR_NOT_OWNED);
        }

        avatarUserRepository.deleteById(associationId);
        return mapper.map(association, AvatarUsuarioResponse.class);
    }

    /**
     * Busca uma associação entre avatar e usuário ou lança exceção 404.
     *
     * @param id identificador da associação
     * @return entidade AvatarUsuario
     */
    private AvatarUsuario findByIdOrThrow(Integer id) {
        return avatarUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }
}
