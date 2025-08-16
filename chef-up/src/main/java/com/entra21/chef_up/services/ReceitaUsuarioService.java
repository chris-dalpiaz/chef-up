package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioRequest;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioResponse;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.entities.ReceitaUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.ReceitaRepository;
import com.entra21.chef_up.repositories.ReceitaUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar a relação entre Receita e Usuario.
 */
@Service
public class ReceitaUsuarioService {

    private static final String ERROR_ASSOCIATION_NOT_FOUND = "Associação não encontrada";
    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado";
    private static final String ERROR_RECIPE_NOT_FOUND = "Receita não encontrada";
    private static final String ERROR_RECIPE_NOT_OWNED = "Receita não pertence ao usuário";

    private final ReceitaUsuarioRepository recipeUserRepository;
    private final ReceitaRepository recipeRepository;
    private final UsuarioRepository userRepository;
    private final ModelMapper mapper;

    public ReceitaUsuarioService(ReceitaUsuarioRepository recipeUserRepository,
                                 ReceitaRepository recipeRepository,
                                 UsuarioRepository userRepository,
                                 ModelMapper mapper) {
        this.recipeUserRepository = recipeUserRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as associações entre receita e usuário para um determinado usuário.
     *
     * @param userId identificador do usuário
     * @return lista de ReceitaUsuarioResponse
     */
    public List<ReceitaUsuarioResponse> listByUser(Integer userId) {
        return recipeUserRepository.findByUsuarioId(userId)
                .stream()
                .map(association -> mapper.map(association, ReceitaUsuarioResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma associação específica entre receita e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação encontrada
     */
    public ReceitaUsuarioResponse getById(Integer userId, Integer associationId) {
        ReceitaUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        return mapper.map(association, ReceitaUsuarioResponse.class);
    }

    /**
     * Cria uma nova associação entre receita e usuário.
     *
     * @param userId  identificador do usuário
     * @param request DTO com o ID da receita e foto do prato
     * @return DTO da associação criada
     */
    public ReceitaUsuarioResponse create(Integer userId, ReceitaUsuarioRequest request) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));

        Receita recipe = recipeRepository.findById(request.getIdReceita())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RECIPE_NOT_FOUND));

        ReceitaUsuario newAssociation = new ReceitaUsuario();
        newAssociation.setUsuario(user);
        newAssociation.setReceita(recipe);
        newAssociation.setDataConclusao(LocalDateTime.now());
        newAssociation.setFotoPrato(request.getFotoPrato());

        ReceitaUsuario saved = recipeUserRepository.save(newAssociation);
        return mapper.map(saved, ReceitaUsuarioResponse.class);
    }

    /**
     * Atualiza uma associação existente entre receita e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @param request       DTO com o novo ID da receita
     * @return DTO da associação atualizada
     */
    public ReceitaUsuarioResponse update(Integer userId, Integer associationId, ReceitaUsuarioRequest request) {
        ReceitaUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        if (request.getIdReceita() != null && !request.getIdReceita().equals(association.getReceita().getId())) {
            Receita newRecipe = recipeRepository.findById(request.getIdReceita())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nova receita não encontrada"));
            association.setReceita(newRecipe);
        }

        ReceitaUsuario updated = recipeUserRepository.save(association);
        return mapper.map(updated, ReceitaUsuarioResponse.class);
    }

    /**
     * Remove uma associação entre receita e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação removida
     */
    @Transactional
    public ReceitaUsuarioResponse delete(Integer userId, Integer associationId) {
        ReceitaUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        recipeUserRepository.deleteById(associationId);
        return mapper.map(association, ReceitaUsuarioResponse.class);
    }

    /**
     * Busca uma associação entre receita e usuário ou lança exceção 404.
     *
     * @param id identificador da associação
     * @return entidade ReceitaUsuario
     */
    private ReceitaUsuario findByIdOrThrow(Integer id) {
        return recipeUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }
}
