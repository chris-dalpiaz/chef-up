package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaRequest;
import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaResponse;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.entities.Utensilio;
import com.entra21.chef_up.entities.UtensilioReceita;
import com.entra21.chef_up.repositories.ReceitaRepository;
import com.entra21.chef_up.repositories.UtensilioReceitaRepository;
import com.entra21.chef_up.repositories.UtensilioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar a relação entre Utensilio e Receita.
 */
@Service
public class UtensilioReceitaService {

    private static final String ERROR_RECIPE_NOT_FOUND = "Receita não encontrada";
    private static final String ERROR_UTENSIL_NOT_FOUND = "Utensílio não encontrado";
    private static final String ERROR_ASSOCIATION_NOT_FOUND = "Utensílio da Receita não encontrado";
    private static final String ERROR_UTENSIL_NOT_OWNED = "Utensílio não pertence à receita informada";

    private final UtensilioRepository utensilRepository;
    private final ReceitaRepository recipeRepository;
    private final UtensilioReceitaRepository utensilRecipeRepository;
    private final ModelMapper mapper;

    public UtensilioReceitaService(UtensilioRepository utensilRepository,
                                   ReceitaRepository recipeRepository,
                                   UtensilioReceitaRepository utensilRecipeRepository,
                                   ModelMapper mapper) {
        this.utensilRepository = utensilRepository;
        this.recipeRepository = recipeRepository;
        this.utensilRecipeRepository = utensilRecipeRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as associações entre utensílio e receita para uma determinada receita.
     *
     * @param recipeId identificador da receita
     * @return lista de UtensilioReceitaResponse
     */
    public List<UtensilioReceitaResponse> listByRecipe(Integer recipeId) {
        return utensilRecipeRepository.findByReceitaId(recipeId)
                .stream()
                .map(association -> mapper.map(association, UtensilioReceitaResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma associação específica entre utensílio e receita.
     *
     * @param recipeId      identificador da receita
     * @param associationId identificador da associação
     * @return DTO da associação encontrada
     */
    public UtensilioReceitaResponse getById(Integer recipeId, Integer associationId) {
        UtensilioReceita association = findByIdOrThrow(associationId);

        if (!association.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_UTENSIL_NOT_OWNED);
        }

        return mapper.map(association, UtensilioReceitaResponse.class);
    }

    /**
     * Cria uma nova associação entre utensílio e receita.
     *
     * @param recipeId identificador da receita
     * @param request  DTO com o ID do utensílio
     * @return DTO da associação criada
     */
    @Transactional
    public UtensilioReceitaResponse create(Integer recipeId, UtensilioReceitaRequest request) {
        Receita recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RECIPE_NOT_FOUND));

        Utensilio utensil = utensilRepository.findById(request.getIdUtensilio())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_UTENSIL_NOT_FOUND));

        UtensilioReceita newAssociation = new UtensilioReceita();
        newAssociation.setReceita(recipe);
        newAssociation.setUtensilio(utensil);

        UtensilioReceita saved = utensilRecipeRepository.save(newAssociation);
        return mapper.map(saved, UtensilioReceitaResponse.class);
    }

    /**
     * Atualiza uma associação existente entre utensílio e receita.
     *
     * @param recipeId      identificador da receita
     * @param associationId identificador da associação
     * @param request       DTO com o novo ID do utensílio
     * @return DTO da associação atualizada
     */
    @Transactional
    public UtensilioReceitaResponse update(Integer recipeId, Integer associationId, UtensilioReceitaRequest request) {
        UtensilioReceita existing = findByIdOrThrow(associationId);

        if (!existing.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_UTENSIL_NOT_OWNED);
        }

        Utensilio newUtensil = utensilRepository.findById(request.getIdUtensilio())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo utensílio não encontrado"));

        existing.setUtensilio(newUtensil);

        utensilRecipeRepository.save(existing);
        return mapper.map(existing, UtensilioReceitaResponse.class);
    }

    /**
     * Remove uma associação entre utensílio e receita.
     *
     * @param recipeId      identificador da receita
     * @param associationId identificador da associação
     * @return DTO da associação removida
     */
    @Transactional
    public UtensilioReceitaResponse delete(Integer recipeId, Integer associationId) {
        UtensilioReceita association = findByIdOrThrow(associationId);

        if (!association.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_UTENSIL_NOT_OWNED);
        }

        utensilRecipeRepository.deleteById(associationId);
        return mapper.map(association, UtensilioReceitaResponse.class);
    }

    /**
     * Busca uma associação entre utensílio e receita ou lança exceção 404.
     *
     * @param id identificador da associação
     * @return entidade UtensilioReceita
     */
    private UtensilioReceita findByIdOrThrow(Integer id) {
        return utensilRecipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }
}
