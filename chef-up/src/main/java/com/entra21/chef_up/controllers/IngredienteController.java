package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Ingrediente.IngredienteRequest;
import com.entra21.chef_up.dtos.Ingrediente.IngredienteResponse;
import com.entra21.chef_up.services.IngredienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações HTTP relacionadas à entidade Ingrediente.
 */
@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    /**
     * Lista todos os ingredientes cadastrados.
     *
     * @return lista com todos ingredientes no banco
     */
    @GetMapping
    public List<IngredienteResponse> listIngredients() {
        return ingredienteService.listAll();
    }

    /**
     * Busca um ingrediente pelo ID.
     * Retorna erro 404 se não encontrado.
     *
     * @param idIngrediente ID do ingrediente na URL
     * @return ingrediente encontrado
     */
    @GetMapping("/{idIngrediente}")
    public IngredienteResponse getIngredient(@PathVariable Integer idIngrediente) {
        return ingredienteService.getById(idIngrediente);
    }

    /**
     * Cria um novo ingrediente.
     *
     * @param request dados do novo ingrediente
     * @return ingrediente criado com ID gerado
     */
    @PostMapping
    public IngredienteResponse createIngredient(@RequestBody IngredienteRequest request) {
        return ingredienteService.create(request);
    }

    /**
     * Atualiza os dados de um ingrediente existente.
     * Retorna erro 404 se não existir.
     *
     * @param idIngrediente ID do ingrediente a ser atualizado
     * @param request       novos dados do ingrediente
     * @return ingrediente atualizado
     */
    @PutMapping("/{idIngrediente}")
    public IngredienteResponse updateIngredient(@PathVariable Integer idIngrediente,
                                                @RequestBody IngredienteRequest request) {
        return ingredienteService.update(idIngrediente, request);
    }

    /**
     * Remove um ingrediente pelo ID.
     * Retorna o ingrediente removido ou erro 404 se não existir.
     *
     * @param idIngrediente ID do ingrediente a ser removido
     * @return ingrediente removido
     */
    @DeleteMapping("/{idIngrediente}")
    public IngredienteResponse deleteIngredient(@PathVariable Integer idIngrediente) {
        return ingredienteService.delete(idIngrediente);
    }
}
