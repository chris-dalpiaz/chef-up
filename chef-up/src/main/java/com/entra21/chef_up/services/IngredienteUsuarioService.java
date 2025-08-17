package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioRequest;
import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioResponse;
import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.entities.IngredienteUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.IngredienteRepository;
import com.entra21.chef_up.repositories.IngredienteUsuarioRepository;
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
 * Serviço responsável por gerenciar a relação entre Ingrediente e Usuario.
 */
@Service
public class IngredienteUsuarioService {

    private static final String ERROR_ASSOCIATION_NOT_FOUND = "Associação não encontrada";
    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado";
    private static final String ERROR_INGREDIENT_NOT_FOUND = "Ingrediente não encontrado";
    private static final String ERROR_INGREDIENT_NOT_OWNED = "Ingrediente não pertence ao usuário";

    private final IngredienteRepository ingredientRepository;
    private final UsuarioRepository userRepository;
    private final IngredienteUsuarioRepository ingredientUserRepository;
    private final ModelMapper mapper;

    public IngredienteUsuarioService(IngredienteRepository ingredientRepository,
                                     UsuarioRepository userRepository,
                                     IngredienteUsuarioRepository ingredientUserRepository,
                                     ModelMapper mapper) {
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
        this.ingredientUserRepository = ingredientUserRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as associações entre ingrediente e usuário para um determinado usuário.
     *
     * @param userId identificador do usuário
     * @return lista de IngredienteUsuarioResponse
     */
    public List<IngredienteUsuarioResponse> listByUser(Integer userId) {
        return ingredientUserRepository.findByUsuarioId(userId)
                .stream()
                .map(association -> mapper.map(association, IngredienteUsuarioResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma associação específica entre ingrediente e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação encontrada
     */
    public IngredienteUsuarioResponse getById(Integer userId, Integer associationId) {
        IngredienteUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_INGREDIENT_NOT_OWNED);
        }

        return mapper.map(association, IngredienteUsuarioResponse.class);
    }

    /**
     * Cria uma nova associação entre ingrediente e usuário.
     *
     * @param userId  identificador do usuário
     * @param request DTO com o ID do ingrediente
     * @return DTO da associação criada
     */
    public IngredienteUsuarioResponse create(Integer userId, IngredienteUsuarioRequest request) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));

        Ingrediente ingredient = ingredientRepository.findById(request.getIdIngrediente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_INGREDIENT_NOT_FOUND));

        IngredienteUsuario newAssociation = new IngredienteUsuario();
        newAssociation.setUsuario(user);
        newAssociation.setIngrediente(ingredient);
        newAssociation.setDataAdicionada(LocalDateTime.now());

        IngredienteUsuario saved = ingredientUserRepository.save(newAssociation);
        return mapper.map(saved, IngredienteUsuarioResponse.class);
    }

    /**
     * Atualiza uma associação existente entre ingrediente e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @param request       DTO com o novo ID do ingrediente
     * @return DTO da associação atualizada
     */
    public IngredienteUsuarioResponse update(Integer userId, Integer associationId, IngredienteUsuarioRequest request) {
        IngredienteUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_INGREDIENT_NOT_OWNED);
        }

        if (request.getIdIngrediente() != null && !request.getIdIngrediente().equals(association.getIngrediente().getId())) {
            Ingrediente newIngredient = ingredientRepository.findById(request.getIdIngrediente())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo ingrediente não encontrado"));
            association.setIngrediente(newIngredient);
        }

        IngredienteUsuario updated = ingredientUserRepository.save(association);
        return mapper.map(updated, IngredienteUsuarioResponse.class);
    }

    /**
     * Remove uma associação entre ingrediente e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação removida
     */
    @Transactional
    public IngredienteUsuarioResponse delete(Integer userId, Integer associationId) {
        IngredienteUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_INGREDIENT_NOT_OWNED);
        }

        ingredientUserRepository.deleteById(associationId);
        return mapper.map(association, IngredienteUsuarioResponse.class);
    }

    /**
     * Busca uma associação entre ingrediente e usuário ou lança exceção 404.
     *
     * @param id identificador da associação
     * @return entidade IngredienteUsuario
     */
    private IngredienteUsuario findByIdOrThrow(Integer id) {
        return ingredientUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }

    public IngredienteUsuarioResponse toResponse(IngredienteUsuario userIngredient) {
        return mapper.map(userIngredient, IngredienteUsuarioResponse.class);
    }

    public List<IngredienteUsuarioResponse> toResponseList(List<IngredienteUsuario> ingredients) {
        return ingredients.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}