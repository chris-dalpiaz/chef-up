package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.ReceitaColecao.ReceitaColecaoRequest;
import com.entra21.chef_up.dtos.ReceitaColecao.ReceitaColecaoResponse;
import com.entra21.chef_up.entities.Colecao;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.entities.ReceitaColecao;
import com.entra21.chef_up.repositories.ColecaoRepository;
import com.entra21.chef_up.repositories.ReceitaColecaoRepository;
import com.entra21.chef_up.repositories.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar a relação entre Receita e Colecao.
 */
@Service
public class ReceitaColecaoService {

    private static final String ERROR_ASSOCIATION_NOT_FOUND = "Associação não encontrada";
    private static final String ERROR_COLLECTION_NOT_FOUND = "Coleção não encontrada";
    private static final String ERROR_RECIPE_NOT_FOUND = "Receita não encontrada";
    private static final String ERROR_RECIPE_NOT_OWNED = "Receita não pertence à coleção informada";

    private final ReceitaColecaoRepository recipeCollectionRepository;
    private final ModelMapper mapper;
    private final ColecaoRepository collectionRepository;
    private final ReceitaRepository recipeRepository;

    public ReceitaColecaoService(ReceitaColecaoRepository recipeCollectionRepository,
                                 ModelMapper mapper,
                                 ColecaoService colecaoService,
                                 ColecaoRepository collectionRepository,
                                 ReceitaService receitaService,
                                 ReceitaRepository recipeRepository) {
        this.recipeCollectionRepository = recipeCollectionRepository;
        this.mapper = mapper;
        this.collectionRepository = collectionRepository;
        this.recipeRepository = recipeRepository;
    }

    /**
     * Lista todas as associações entre receita e coleção para uma determinada coleção.
     *
     * @param collectionId identificador da coleção
     * @return lista de ReceitaColecaoResponse
     */
    public List<ReceitaColecaoResponse> listAll(Integer collectionId) {
        return recipeCollectionRepository.findByColecaoId(collectionId)
                .stream()
                .map(association -> mapper.map(association, ReceitaColecaoResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma associação específica entre receita e coleção.
     *
     * @param collectionId  identificador da coleção
     * @param associationId identificador da associação
     * @return DTO da associação encontrada
     */
    public ReceitaColecaoResponse getById(Integer collectionId, Integer associationId) {
        ReceitaColecao association = findByIdOrThrow(associationId);

        if (!association.getColecao().getId().equals(collectionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        return mapper.map(association, ReceitaColecaoResponse.class);
    }

    /**
     * Cria uma nova associação entre receita e coleção.
     *
     * @param collectionId identificador da coleção
     * @param request      DTO com o ID da receita
     * @return DTO da associação criada
     */
    public ReceitaColecaoResponse create(Integer collectionId, ReceitaColecaoRequest request) {
        Colecao collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_COLLECTION_NOT_FOUND));

        Receita recipe = recipeRepository.findById(request.getIdReceita())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RECIPE_NOT_FOUND));

        ReceitaColecao newAssociation = new ReceitaColecao();
        newAssociation.setColecao(collection);
        newAssociation.setReceita(recipe);

        ReceitaColecao saved = recipeCollectionRepository.save(newAssociation);
        return mapper.map(saved, ReceitaColecaoResponse.class);
    }

    /**
     * Atualiza uma associação existente entre receita e coleção.
     *
     * @param collectionId  identificador da coleção
     * @param associationId identificador da associação
     * @param request       DTO com o novo ID da receita
     * @return DTO da associação atualizada
     */
    public ReceitaColecaoResponse update(Integer collectionId, Integer associationId, ReceitaColecaoRequest request) {
        ReceitaColecao association = findByIdOrThrow(associationId);

        if (!association.getColecao().getId().equals(collectionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        if (request.getIdReceita() != null && !request.getIdReceita().equals(association.getReceita().getId())) {
            Receita newRecipe = recipeRepository.findById(request.getIdReceita())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nova receita não encontrada"));
            association.setReceita(newRecipe);
        }

        ReceitaColecao updated = recipeCollectionRepository.save(association);
        return mapper.map(updated, ReceitaColecaoResponse.class);
    }

    /**
     * Remove uma associação entre receita e coleção.
     *
     * @param collectionId  identificador da coleção
     * @param associationId identificador da associação
     * @return DTO da associação removida
     */
    @Transactional
    public ReceitaColecaoResponse delete(Integer collectionId, Integer associationId) {
        ReceitaColecao association = findByIdOrThrow(associationId);

        if (!association.getColecao().getId().equals(collectionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_OWNED);
        }

        recipeCollectionRepository.deleteById(associationId);
        return mapper.map(association, ReceitaColecaoResponse.class);
    }

    /**
     * Busca uma associação entre receita e coleção ou lança exceção 404.
     *
     * @param id identificador da associação
     * @return entidade ReceitaColecao
     */
    private ReceitaColecao findByIdOrThrow(Integer id) {
        return recipeCollectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }
}
