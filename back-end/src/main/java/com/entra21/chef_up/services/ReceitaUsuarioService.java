package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
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

    public List<ReceitaUsuarioResponse> listByUser(Integer userId) {
        return recipeUserRepository.findByUsuarioId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ReceitaUsuarioResponse getById(Integer userId, Integer associationId) {
        ReceitaUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        return toResponse(association);
    }

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
        newAssociation.setPontuacaoPrato(request.getPontuacaoPrato());
        newAssociation.setTextoAvaliacao(request.getTextoAvaliacao()); // <-- salvar avaliação IA

        ReceitaUsuario saved = recipeUserRepository.save(newAssociation);
        return toResponse(saved);
    }

    public ReceitaUsuarioResponse update(Integer userId, Integer associationId, ReceitaUsuarioRequest request) {
        ReceitaUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        // Atualiza a receita associada, se necessário
        if (request.getIdReceita() != null && !request.getIdReceita().equals(association.getReceita().getId())) {
            Receita newRecipe = recipeRepository.findById(request.getIdReceita())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nova receita não encontrada"));
            association.setReceita(newRecipe);
        }

        // Atualiza a foto do prato, se fornecida
        if (request.getFotoPrato() != null && !request.getFotoPrato().isBlank()) {
            association.setFotoPrato(request.getFotoPrato());
        }

        // Atualiza a pontuação do prato, se fornecida
        if (request.getPontuacaoPrato() != null) {
            association.setPontuacaoPrato(request.getPontuacaoPrato());
        }

        if (request.getTextoAvaliacao() != null && !request.getTextoAvaliacao().isBlank()) {
            association.setTextoAvaliacao(request.getTextoAvaliacao());
        }

        ReceitaUsuario updated = recipeUserRepository.save(association);
        return toResponse(updated);
    }

    @Transactional
    public ReceitaUsuarioResponse delete(Integer userId, Integer associationId) {
        ReceitaUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        recipeUserRepository.deleteById(associationId);
        return toResponse(association);
    }

    public ReceitaUsuarioResponse toResponse(ReceitaUsuario userRecipe) {
        ReceitaUsuarioResponse response = mapper.map(userRecipe, ReceitaUsuarioResponse.class);

        if (userRecipe.getReceita() != null) {
            response.setReceita(mapper.map(userRecipe.getReceita(), ReceitaResponse.class));
        }

        return response;
    }

    public List<ReceitaUsuarioResponse> toResponseList(List<ReceitaUsuario> recipes) {
        return recipes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ReceitaUsuario findByIdOrThrow(Integer id) {
        return recipeUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }
}