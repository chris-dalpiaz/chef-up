package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Receita.ReceitaRequest;
import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import com.entra21.chef_up.entities.Categoria;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações CRUD de Receita.
 */
@Service
public class ReceitaService {

    private static final String ERROR_RECIPE_NOT_FOUND = "Receita não encontrada";

    private final ReceitaRepository recipeRepository;
    private final ModelMapper mapper;
    private final CategoriaService categoryService;
    private final IngredienteReceitaRepository ingredientRecipeRepository;
    private final EtapaReceitaRepository stepRecipeRepository;
    private final ReceitaUsuarioRepository userRecipeRepository;
    private final ReceitaColecaoRepository collectionRecipeRepository;
    private final UtensilioReceitaRepository utensilRecipeRepository;

    public ReceitaService(ReceitaRepository recipeRepository,
                          ModelMapper mapper,
                          CategoriaService categoryService,
                          IngredienteReceitaRepository ingredientRecipeRepository,
                          EtapaReceitaRepository stepRecipeRepository,
                          ReceitaUsuarioRepository userRecipeRepository,
                          ReceitaColecaoRepository collectionRecipeRepository,
                          UtensilioReceitaRepository utensilRecipeRepository) {
        this.recipeRepository = recipeRepository;
        this.mapper = mapper;
        this.categoryService = categoryService;
        this.ingredientRecipeRepository = ingredientRecipeRepository;
        this.stepRecipeRepository = stepRecipeRepository;
        this.userRecipeRepository = userRecipeRepository;
        this.collectionRecipeRepository = collectionRecipeRepository;
        this.utensilRecipeRepository = utensilRecipeRepository;
    }

    /**
     * Retorna todas as receitas cadastradas, incluindo a categoria mapeada.
     *
     * @return lista de ReceitaResponse
     */
    public List<ReceitaResponse> listAll() {
        return recipeRepository.findAll()
                .stream()
                .map(recipe -> {
                    ReceitaResponse response = mapper.map(recipe, ReceitaResponse.class);
                    response.setCategoria(categoryService.toResponse(recipe.getCategoria()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    /**
     * Busca uma receita pelo seu identificador.
     *
     * @param id identificador da receita
     * @return DTO contendo os dados da receita
     * @throws ResponseStatusException se a receita não for encontrada
     */
    public ReceitaResponse getById(Integer id) {
        Receita recipe = findEntityById(id);
        return mapper.map(recipe, ReceitaResponse.class);
    }

    /**
     * Cria uma nova receita com base nos dados fornecidos.
     *
     * @param request DTO contendo as informações para criação
     * @return DTO da receita criada
     */
    public ReceitaResponse create(ReceitaRequest request) {
        Receita entity = mapper.map(request, Receita.class);
        Receita saved = recipeRepository.save(entity);
        return mapper.map(saved, ReceitaResponse.class);
    }

    /**
     * Atualiza os dados de uma receita existente.
     *
     * @param id      identificador da receita a ser atualizada
     * @param request DTO contendo os novos dados
     * @return DTO da receita atualizada
     * @throws ResponseStatusException se a receita não for encontrada
     */
    public ReceitaResponse update(Integer id, ReceitaRequest request) {
        Receita entity = findEntityById(id);

        entity.setNome(request.getNome());
        entity.setDificuldade(request.getDificuldade());
        entity.setDescricao(request.getDescricao());
        entity.setXpGanho(request.getXpGanho());
        entity.setTempoPreparoSegundos(request.getTempoPreparoSegundos());

        Categoria category = categoryService.findByIdOrThrow(request.getIdCategoria());
        entity.setCategoria(category);

        Receita updated = recipeRepository.save(entity);
        return mapper.map(updated, ReceitaResponse.class);
    }

    /**
     * Remove uma receita e todas as dependências relacionadas.
     *
     * @param id identificador da receita a ser removida
     * @return DTO da receita removida
     * @throws ResponseStatusException se a receita não for encontrada
     */
    @Transactional
    public ReceitaResponse delete(Integer id) {
        Receita entity = findEntityById(id);

        // Remove registros relacionados à receita em outras tabelas
        ingredientRecipeRepository.removeByReceitaId(id);
        stepRecipeRepository.removeByReceitaId(id);
        userRecipeRepository.removeByReceitaId(id);
        collectionRecipeRepository.removeByReceitaId(id);
        utensilRecipeRepository.removeByReceitaId(id);

        recipeRepository.deleteById(id);
        return mapper.map(entity, ReceitaResponse.class);
    }

    /**
     * Busca a entidade Receita pelo ID ou lança exceção 404.
     *
     * @param id identificador da receita
     * @return entidade encontrada
     * @throws ResponseStatusException se não encontrar a entidade
     */
    private Receita findEntityById(Integer id) {
        return recipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RECIPE_NOT_FOUND)
                );
    }
}