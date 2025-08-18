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

    public List<AvatarUsuarioResponse> listByUser(Integer userId) {
        return avatarUserRepository.findByUsuarioId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AvatarUsuarioResponse getById(Integer userId, Integer associationId) {
        AvatarUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_AVATAR_NOT_OWNED);
        }

        return toResponse(association);
    }

    public AvatarUsuarioResponse create(Integer userId, AvatarUsuarioRequest request) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));

        Avatar avatar = avatarRepository.findById(request.getIdAvatar())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_AVATAR_NOT_FOUND));

        AvatarUsuario newAssociation = new AvatarUsuario();
        newAssociation.setUsuario(user);
        newAssociation.setAvatar(avatar);
        newAssociation.setEstaAtivo(false);

        AvatarUsuario saved = avatarUserRepository.save(newAssociation);
        return toResponse(saved);
    }

    /**
     * Atualiza o avatar ativo do usuário com base no ID do avatar.
     * Este método assume que o avatar já está desbloqueado para o usuário.
     */
    public AvatarUsuarioResponse update(Integer userId, Integer avatarId, AvatarUsuarioRequest request) {
        AvatarUsuario association = findByUsuarioIdAndAvatarIdOrThrow(userId, avatarId);

        if (request.getEstaAtivo() != null) {
            // Desativa todos os outros avatares do usuário
            List<AvatarUsuario> todos = avatarUserRepository.findByUsuarioId(userId);
            todos.forEach(a -> a.setEstaAtivo(false));
            association.setEstaAtivo(true);
            todos.add(association); // garante que o avatar selecionado está na lista

            avatarUserRepository.saveAll(todos);
        }

        return toResponse(association);
    }

    @Transactional
    public AvatarUsuarioResponse delete(Integer userId, Integer associationId) {
        AvatarUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_AVATAR_NOT_OWNED);
        }

        avatarUserRepository.deleteById(associationId);
        return toResponse(association);
    }

    private AvatarUsuario findByIdOrThrow(Integer id) {
        return avatarUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }

    private AvatarUsuario findByUsuarioIdAndAvatarIdOrThrow(Integer usuarioId, Integer avatarId) {
        return avatarUserRepository.findByUsuarioIdAndAvatarId(usuarioId, avatarId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }

    public AvatarUsuarioResponse toResponse(AvatarUsuario userAvatar) {
        return mapper.map(userAvatar, AvatarUsuarioResponse.class);
    }

    public List<AvatarUsuarioResponse> toResponseList(List<AvatarUsuario> avatars) {
        return avatars.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}