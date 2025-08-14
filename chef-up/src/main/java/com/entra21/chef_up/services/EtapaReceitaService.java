package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaRequest;
import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.entities.EtapaReceita;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.repositories.EtapaReceitaRepository;
import com.entra21.chef_up.repositories.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar entidades EtapaReceita.
 */
@Service
public class EtapaReceitaService {

    private static final String ERROR_STEP_NOT_FOUND = "Etapa não encontrada";
    private static final String ERROR_RECIPE_NOT_FOUND = "Receita não encontrada";
    private static final String ERROR_STEP_NOT_OWNED = "Etapa não pertence à receita informada";

    private final EtapaReceitaRepository stepRepository;
    private final ReceitaRepository recipeRepository;
    private final ModelMapper mapper;

    public EtapaReceitaService(EtapaReceitaRepository stepRepository,
                               ReceitaRepository recipeRepository,
                               ModelMapper mapper) {
        this.stepRepository = stepRepository;
        this.recipeRepository = recipeRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as etapas de uma determinada receita.
     *
     * @param recipeId identificador da receita
     * @return lista de EtapaReceitaResponse
     */
    public List<EtapaReceitaResponse> listByRecipe(Integer recipeId) {
        return stepRepository.findByReceitaId(recipeId)
                .stream()
                .map(step -> mapper.map(step, EtapaReceitaResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma etapa específica pelo seu ID e pela receita.
     *
     * @param recipeId identificador da receita
     * @param stepId   identificador da etapa
     * @return DTO da etapa encontrada
     */
    public EtapaReceitaResponse getById(Integer recipeId, Integer stepId) {
        EtapaReceita step = findByIdOrThrow(stepId);

        if (!step.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_STEP_NOT_OWNED);
        }

        return mapper.map(step, EtapaReceitaResponse.class);
    }

    /**
     * Cria uma nova etapa para uma determinada receita.
     *
     * @param recipeId identificador da receita
     * @param request  DTO com os dados da etapa
     * @return DTO da etapa criada
     */
    @Transactional
    public EtapaReceitaResponse create(Integer recipeId, EtapaReceitaRequest request) {
        Receita recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_RECIPE_NOT_FOUND));

        EtapaReceita newStep = new EtapaReceita();
        newStep.setReceita(recipe);
        newStep.setOrdem(request.getOrdem());
        newStep.setConteudo(request.getConteudo());

        EtapaReceita saved = stepRepository.save(newStep);
        return mapper.map(saved, EtapaReceitaResponse.class);
    }

    /**
     * Atualiza uma etapa existente.
     *
     * @param recipeId identificador da receita
     * @param stepId   identificador da etapa
     * @param request  DTO com os dados atualizados
     * @return DTO da etapa atualizada
     */
    @Transactional
    public EtapaReceitaResponse update(Integer recipeId, Integer stepId, EtapaReceitaRequest request) {
        EtapaReceita existingStep = findByIdOrThrow(stepId);

        if (!existingStep.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_STEP_NOT_OWNED);
        }

        existingStep.setOrdem(request.getOrdem());
        existingStep.setConteudo(request.getConteudo());

        stepRepository.save(existingStep);
        return mapper.map(existingStep, EtapaReceitaResponse.class);
    }

    /**
     * Remove uma etapa pelo seu ID.
     *
     * @param stepId identificador da etapa
     * @return DTO da etapa removida
     */
    @Transactional
    public EtapaReceitaResponse delete(Integer stepId) {
        EtapaReceita step = findByIdOrThrow(stepId);
        stepRepository.deleteById(stepId);
        return mapper.map(step, EtapaReceitaResponse.class);
    }

    /**
     * Busca uma etapa ou lança exceção 404.
     *
     * @param id identificador da etapa
     * @return entidade EtapaReceita
     */
    private EtapaReceita findByIdOrThrow(Integer id) {
        return stepRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_STEP_NOT_FOUND));
    }
}