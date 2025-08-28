package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaRequest;
import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.dtos.IngredienteEtapaReceita.IngredienteEtapaReceitaRequest;
import com.entra21.chef_up.dtos.IngredienteEtapaReceita.IngredienteEtapaReceitaResponse;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaResponse;
import com.entra21.chef_up.entities.*;
import com.entra21.chef_up.repositories.EtapaReceitaRepository;
import com.entra21.chef_up.repositories.IngredienteEtapaReceitaRepository;
import com.entra21.chef_up.repositories.IngredienteReceitaRepository;
import com.entra21.chef_up.repositories.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredienteEtapaReceitaService {

    private static final String ERROR_STEP_NOT_FOUND = "Etapa não encontrada para a receita informada";
    private static final String ERROR_INGREDIENT_NOT_FOUND = "Ingrediente não encontrado";
    private static final String ERROR_INGREDIENT_NOT_IN_RECIPE = "Ingrediente não pertence à receita informada";
    private static final String ERROR_RECIPE_NOT_FOUND = "Receita não encontrada";
    private static final String ERROR_STEP_NOT_OWNED = "Etapa não pertence à receita informada";

    private final EtapaReceitaRepository stepRepository;
    private final ReceitaRepository recipeRepository;
    private final IngredienteReceitaRepository recipeIngredientRepository;
    private final IngredienteEtapaReceitaRepository stepIngredientRepository;
    private final ModelMapper mapper;

    public IngredienteEtapaReceitaService(EtapaReceitaRepository stepRepository, ReceitaRepository recipeRepository, IngredienteReceitaRepository recipeIngredientRepository, IngredienteEtapaReceitaRepository stepIngredientRepository, ModelMapper mapper) {
        this.stepRepository = stepRepository;
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.stepIngredientRepository = stepIngredientRepository;
        this.mapper = mapper;
    }

    public List<IngredienteEtapaReceitaResponse> listIngredientsByStep(Integer recipeId, Integer stepId) {
        // Verifica se a etapa existe e pertence à receita
        Optional<EtapaReceita> etapa = stepRepository.findById(stepId);
        if (etapa.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_STEP_NOT_FOUND);
        }

        if (!etapa.get().getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_STEP_NOT_OWNED);
        }

        // Busca os ingredientes vinculados à etapa
        List<IngredienteEtapaReceita> ingredientes = stepIngredientRepository.findByEtapaReceitaId(stepId);

        return ingredientes.stream()
                .map(ingrediente -> mapper.map(ingrediente, IngredienteEtapaReceitaResponse.class))
                .collect(Collectors.toList());
    }


    public IngredienteEtapaReceitaResponse getById(Integer recipeId, Integer stepId, Integer ingredientId) {
        // Busca o ingrediente da etapa
        Optional<IngredienteEtapaReceita> ingredienteOpt = stepIngredientRepository.findById(ingredientId);

        if (ingredienteOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_INGREDIENT_NOT_FOUND);
        }

        IngredienteEtapaReceita ingrediente = ingredienteOpt.get();

        // Valida se o ingrediente pertence à etapa informada
        EtapaReceita etapa = ingrediente.getEtapaReceita();
        if (etapa == null || !etapa.getId().equals(stepId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_STEP_NOT_OWNED);
        }

        // Valida se a etapa pertence à receita informada
        Receita receita = etapa.getReceita();
        if (receita == null || !receita.getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_RECIPE_NOT_FOUND);
        }

        return mapper.map(ingrediente, IngredienteEtapaReceitaResponse.class);
    }

    @Transactional
    public IngredienteEtapaReceitaResponse create(Integer recipeId, Integer stepId, IngredienteEtapaReceitaRequest request) {
        // Verifica se a etapa existe
        EtapaReceita etapa = stepRepository.findById(stepId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_STEP_NOT_FOUND));

        // Verifica se a etapa pertence à receita informada
        if (!etapa.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_STEP_NOT_OWNED);
        }

        // Verifica se o ingrediente da receita existe
        IngredienteReceita ingredienteReceita = recipeIngredientRepository.findById(request.getIdIngredienteReceita())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_INGREDIENT_NOT_FOUND));

        // Verifica se o ingrediente pertence à mesma receita da etapa
        if (!ingredienteReceita.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_INGREDIENT_NOT_IN_RECIPE);
        }

        // Cria o vínculo entre ingrediente da receita e etapa da receita
        IngredienteEtapaReceita novoIngredienteEtapa = new IngredienteEtapaReceita();
        novoIngredienteEtapa.setEtapaReceita(etapa);
        novoIngredienteEtapa.setIngredienteReceita(ingredienteReceita);

        IngredienteEtapaReceita salvo = stepIngredientRepository.save(novoIngredienteEtapa);
        return mapper.map(salvo, IngredienteEtapaReceitaResponse.class);
    }

    @Transactional
    public IngredienteEtapaReceitaResponse update(Integer recipeId, Integer stepId, Integer ingredienteEtapaId, IngredienteEtapaReceitaRequest request) {
        // Busca a etapa da receita
        EtapaReceita etapa = stepRepository.findById(stepId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_STEP_NOT_FOUND));

        // Verifica se a etapa pertence à receita informada
        if (!etapa.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_STEP_NOT_OWNED);
        }

        // Busca o vínculo existente entre ingrediente e etapa
        IngredienteEtapaReceita existente = stepIngredientRepository.findById(ingredienteEtapaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vínculo ingrediente-etapa não encontrado"));

        // Verifica se o vínculo pertence à mesma etapa
        if (!existente.getEtapaReceita().getId().equals(stepId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vínculo não pertence à etapa informada");
        }

        // Busca o novo ingrediente da receita (caso esteja sendo alterado)
        IngredienteReceita novoIngrediente = recipeIngredientRepository.findById(request.getIdIngredienteReceita())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente da receita não encontrado"));

        // Verifica se o ingrediente pertence à mesma receita
        if (!novoIngrediente.getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_INGREDIENT_NOT_IN_RECIPE);
        }

        // Atualiza o vínculo
        existente.setIngredienteReceita(novoIngrediente);

        IngredienteEtapaReceita atualizado = stepIngredientRepository.save(existente);
        return mapper.map(atualizado, IngredienteEtapaReceitaResponse.class);
    }

    /**
     * Remove o vínculo entre um ingrediente da receita e uma etapa específica.
     *
     * @param recipeId           ID da receita
     * @param stepId             ID da etapa da receita
     * @param ingredienteEtapaId ID do vínculo ingrediente-etapa
     * @return DTO do vínculo removido
     */
    @Transactional
    public IngredienteEtapaReceitaResponse delete(Integer recipeId, Integer stepId, Integer ingredienteEtapaId) {
        IngredienteEtapaReceita vinculo = findByIdOrThrow(ingredienteEtapaId);

        // Verifica se a etapa pertence à receita
        if (!vinculo.getEtapaReceita().getId().equals(stepId) ||
                !vinculo.getEtapaReceita().getReceita().getId().equals(recipeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vínculo não pertence à etapa ou receita informada");
        }

        stepIngredientRepository.deleteById(ingredienteEtapaId);
        return mapper.map(vinculo, IngredienteEtapaReceitaResponse.class);
    }

    /**
     * Busca um vínculo ingrediente-etapa ou lança exceção 404.
     *
     * @param id identificador do vínculo
     * @return entidade IngredienteEtapaReceita
     */
    private IngredienteEtapaReceita findByIdOrThrow(Integer id) {
        return stepIngredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vínculo ingrediente-etapa não encontrado"));
    }
}

