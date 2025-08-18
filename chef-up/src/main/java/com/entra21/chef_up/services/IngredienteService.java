package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Ingrediente.IngredienteRequest;
import com.entra21.chef_up.dtos.Ingrediente.IngredienteResponse;
import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.repositories.IngredienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações de CRUD para Ingrediente.
 */
@Service
public class IngredienteService {

    private static final String ERROR_INGREDIENT_NOT_FOUND = "Ingrediente não encontrado";

    private final IngredienteRepository ingredientRepository;
    private final ModelMapper mapper;

    public IngredienteService(IngredienteRepository ingredientRepository,
                              ModelMapper mapper) {
        this.ingredientRepository = ingredientRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todos os ingredientes cadastrados.
     *
     * @return lista de IngredienteResponse
     */
    public List<IngredienteResponse> listAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredient -> mapper.map(ingredient, IngredienteResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca um ingrediente pelo seu ID.
     *
     * @param id identificador do ingrediente
     * @return DTO do ingrediente encontrado
     */
    public IngredienteResponse getById(Integer id) {
        Ingrediente ingredient = findByIdOrThrow(id);
        return mapper.map(ingredient, IngredienteResponse.class);
    }

    /**
     * Cria um novo ingrediente.
     *
     * @param request DTO com os dados do ingrediente
     * @return DTO do ingrediente criado
     */
    public IngredienteResponse create(IngredienteRequest request) {
        Ingrediente newIngredient = mapper.map(request, Ingrediente.class);
        Ingrediente saved = ingredientRepository.save(newIngredient);
        return mapper.map(saved, IngredienteResponse.class);
    }

    /**
     * Atualiza um ingrediente existente.
     *
     * @param id      identificador do ingrediente
     * @param request DTO com os dados atualizados
     * @return DTO do ingrediente atualizado
     */
    public IngredienteResponse update(Integer id, IngredienteRequest request) {
        Ingrediente existing = findByIdOrThrow(id);

        existing.setNome(request.getNome());
        existing.setCategoria(request.getCategoria());
        existing.setDicaConservacao(request.getDicaConservacao());
        existing.setEstimativaValidade(request.getEstimativaValidade());

        Ingrediente updated = ingredientRepository.save(existing);
        return mapper.map(updated, IngredienteResponse.class);
    }

    /**
     * Remove um ingrediente pelo ID.
     *
     * @param id identificador do ingrediente
     * @return DTO do ingrediente removido
     */
    public IngredienteResponse delete(Integer id) {
        Ingrediente ingredient = findByIdOrThrow(id);
        ingredientRepository.deleteById(id);
        return mapper.map(ingredient, IngredienteResponse.class);
    }

    /**
     * Busca o ingrediente ou lança exceção 404.
     *
     * @param id identificador
     * @return entidade Ingrediente
     */
    private Ingrediente findByIdOrThrow(Integer id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_INGREDIENT_NOT_FOUND));
    }
}
