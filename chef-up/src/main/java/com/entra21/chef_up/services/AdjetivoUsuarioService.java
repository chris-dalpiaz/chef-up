package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioRequest;
import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioResponse;
import com.entra21.chef_up.entities.Adjetivo;
import com.entra21.chef_up.entities.AdjetivoUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.AdjetivoRepository;
import com.entra21.chef_up.repositories.AdjetivoUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações CRUD de associação entre usuário e adjetivo.
 */
@Service
public class AdjetivoUsuarioService {

    private static final String ERROR_ASSOCIATION_NOT_FOUND = "Associação não encontrada";
    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado";
    private static final String ERROR_ADJECTIVE_NOT_FOUND = "Adjetivo não encontrado";
    private static final String ERROR_ADJECTIVE_NOT_BELONGS_TO_USER = "Adjetivo não pertence ao usuário";

    private final UsuarioRepository userRepository;
    private final AdjetivoUsuarioRepository adjectiveUserRepository;
    private final AdjetivoRepository adjectiveRepository;
    private final ModelMapper mapper;

    public AdjetivoUsuarioService(UsuarioRepository userRepository,
                                  AdjetivoUsuarioRepository adjectiveUserRepository,
                                  AdjetivoRepository adjectiveRepository,
                                  ModelMapper mapper) {
        this.userRepository = userRepository;
        this.adjectiveUserRepository = adjectiveUserRepository;
        this.adjectiveRepository = adjectiveRepository;
        this.mapper = mapper;
    }

    /**
     * Retorna todas as associações de adjetivo de um usuário.
     *
     * @param userId identificador do usuário
     * @return lista de AdjetivoUsuarioResponse
     */
    public List<AdjetivoUsuarioResponse> listByUser(Integer userId) {
        return adjectiveUserRepository.findByUsuarioId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retorna uma associação de adjetivo de um usuário pelo ID da associação.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação encontrada
     */
    public AdjetivoUsuarioResponse getById(Integer userId, Integer associationId) {
        AdjetivoUsuario association = findAssociationByIdAndValidateUser(userId, associationId);
        return toResponse(association);
    }

    /**
     * Cria uma nova associação entre usuário e adjetivo.
     *
     * @param userId  identificador do usuário
     * @param request DTO contendo o ID do adjetivo
     * @return DTO da associação criada
     */
    public AdjetivoUsuarioResponse create(Integer userId, AdjetivoUsuarioRequest request) {
        Usuario user = findUser(userId);
        Adjetivo adjective = findAdjective(request.getIdAdjetivo());

        AdjetivoUsuario association = new AdjetivoUsuario();
        association.setUsuario(user);
        association.setAdjetivo(adjective);

        AdjetivoUsuario saved = adjectiveUserRepository.save(association);
        return toResponse(saved);
    }

    /**
     * Atualiza o adjetivo de uma associação existente.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @param request       DTO contendo o novo ID do adjetivo
     * @return DTO da associação atualizada
     */
    public AdjetivoUsuarioResponse update(Integer userId, Integer associationId, AdjetivoUsuarioRequest request) {
        AdjetivoUsuario association = findAssociationByIdAndValidateUser(userId, associationId);

        if (request.getIdAdjetivo() != null &&
                !request.getIdAdjetivo().equals(association.getAdjetivo().getId())) {
            Adjetivo newAdjective = findAdjective(request.getIdAdjetivo());
            association.setAdjetivo(newAdjective);
        }

        AdjetivoUsuario updated = adjectiveUserRepository.save(association);
        return toResponse(updated);
    }

    /**
     * Remove uma associação entre usuário e adjetivo.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação removida
     */
    @Transactional
    public AdjetivoUsuarioResponse delete(Integer userId, Integer associationId) {
        AdjetivoUsuario association = findAssociationByIdAndValidateUser(userId, associationId);
        adjectiveUserRepository.deleteById(associationId);
        return toResponse(association);
    }

    public AdjetivoUsuarioResponse toResponse(AdjetivoUsuario userAdjective) {
        return mapper.map(userAdjective, AdjetivoUsuarioResponse.class);
    }

    public List<AdjetivoUsuarioResponse> toResponseList(List<AdjetivoUsuario> adjectives) {
        return adjectives.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um usuário por ID ou lança exceção 404.
     *
     * @param userId identificador do usuário
     * @return entidade Usuario
     */
    public Usuario findUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));
    }

    /**
     * Busca um adjetivo por ID ou lança exceção 404.
     *
     * @param adjectiveId identificador do adjetivo
     * @return entidade Adjetivo
     */
    public Adjetivo findAdjective(Integer adjectiveId) {
        return adjectiveRepository.findById(adjectiveId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ADJECTIVE_NOT_FOUND));
    }

    /**
     * Busca associação por ID e valida se pertence ao usuário informado.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return entidade AdjetivoUsuario
     */
    public AdjetivoUsuario findAssociationByIdAndValidateUser(Integer userId, Integer associationId) {
        AdjetivoUsuario association = adjectiveUserRepository.findById(associationId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_ADJECTIVE_NOT_BELONGS_TO_USER);
        }
        return association;
    }
}
