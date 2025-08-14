package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaRequest;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaResponse;
import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.entities.IngredienteReceita;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.repositories.IngredienteReceitaRepository;
import com.entra21.chef_up.repositories.IngredienteRepository;
import com.entra21.chef_up.repositories.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar entidades IngredienteReceita.
 */
@Service
public class IngredienteReceitaService {

    private static final String ERROR_RECIPE_NOT_FOUND = "Receita não encontrada";
    private static final String ERROR_INGREDIENT_NOT_FOUND = "Ingrediente não encontrado";
    private static final String ERROR_INGREDIENT_NOT_OWNED = "Ingrediente não pertence à receita informada";

    private final IngredienteReceitaRepository ingredientRecipeRepository;
    private final IngredienteRepository ingredientRepository;
    private final ReceitaRepository recipeRepository;
    private final ModelMapper mapper;

    public IngredienteReceitaService(IngredienteReceitaRepository ingredientRecipeRepository,
                                     IngredienteRepository ingredientRepository,
                                     ReceitaRepository recipeRepository,
                                     ModelMapper mapper) {
        this.ingredientRecipeRepository = ingredientRecipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todos os ingredientes de uma determinada receita.
     *
     * @param recipeId identificador da receita
     * @return lista de IngredienteReceitaResponse
     */
    public List<IngredienteReceitaResponse> listByRecipe(Integer recipeId) {
        return ingredientRecipeRepository.findByReceitaId(recipeId)
                .stream()
                .map(ingredient -> mapper.map(ingredient, IngredienteReceitaResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma associação específica entre ingrediente e receita.
     *
     * @param recipeId           identificador da receita
     * @param ingredientRecipeId identificador da associação
     * @return DTO da associação encontrada
     */
    public IngredienteReceitaResponse getById(Integer recipeId, Integer ingredientRecipeId) {
        IngredienteReceita association = findByIdOrThrow(ingredientRecipeId);

        if (!association.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_INGREDIENT_NOT_OWNED);
        }

        return mapper.map(association, IngredienteReceitaResponse.class);
    }

    /**
     * Cria uma nova associação entre ingrediente e receita.
     *
     * @param recipeId identificador da receita
     * @param request  DTO com os dados do ingrediente
     * @return DTO da associação criada
     */
    @Transactional
    public IngredienteReceitaResponse create(Integer recipeId, IngredienteReceitaRequest request) {
        Receita recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RECIPE_NOT_FOUND));

        Ingrediente ingredient = ingredientRepository.findById(request.getIdIngrediente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_INGREDIENT_NOT_FOUND));

        IngredienteReceita newAssociation = new IngredienteReceita();
        newAssociation.setReceita(recipe);
        newAssociation.setIngrediente(ingredient);
        newAssociation.setQuantidade(request.getQuantidade());
        newAssociation.setUnidadeMedida(request.getUnidadeMedida());

        IngredienteReceita saved = ingredientRecipeRepository.save(newAssociation);
        return mapper.map(saved, IngredienteReceitaResponse.class);
    }

    /**
     * Atualiza uma associação existente entre ingrediente e receita.
     *
     * @param recipeId           identificador da receita
     * @param ingredientRecipeId identificador da associação
     * @param request            DTO com os dados atualizados
     * @return DTO da associação atualizada
     */
    @Transactional
    public IngredienteReceitaResponse update(Integer recipeId, Integer ingredientRecipeId, IngredienteReceitaRequest request) {
        IngredienteReceita existing = findByIdOrThrow(ingredientRecipeId);

        if (!existing.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_INGREDIENT_NOT_OWNED);
        }

        existing.setQuantidade(request.getQuantidade());
        existing.setUnidadeMedida(request.getUnidadeMedida());

        ingredientRecipeRepository.save(existing);
        return mapper.map(existing, IngredienteReceitaResponse.class);
    }

    /**
     * Remove uma associação entre ingrediente e receita.
     *
     * @param recipeId           identificador da receita
     * @param ingredientRecipeId identificador da associação
     * @return DTO da associação removida
     */
    @Transactional
    public IngredienteReceitaResponse delete(Integer recipeId, Integer ingredientRecipeId) {
        IngredienteReceita association = findByIdOrThrow(ingredientRecipeId);

        if (!association.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_INGREDIENT_NOT_OWNED);
        }

        ingredientRecipeRepository.deleteById(ingredientRecipeId);
        return mapper.map(association, IngredienteReceitaResponse.class);
    }

    /**
     * Busca uma associação entre ingrediente e receita ou lança exceção 404.
     *
     * @param id identificador da associação
     * @return entidade IngredienteReceita
     */
    private IngredienteReceita findByIdOrThrow(Integer id) {
        return ingredientRecipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_INGREDIENT_NOT_FOUND));
    }
}
