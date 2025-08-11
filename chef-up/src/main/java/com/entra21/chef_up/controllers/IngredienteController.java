package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Ingrediente.IngredienteRequest;
import com.entra21.chef_up.dtos.Ingrediente.IngredienteResponse;
import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.repositories.IngredienteRepository;
import com.entra21.chef_up.services.IngredienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public List<IngredienteResponse> listarIngredientes() {
        return ingredienteService.listarTodos();
    }

    /**
     * Busca um ingrediente pelo ID.
     * Retorna erro 404 se não encontrado.
     *
     * @param idIngrediente ID do ingrediente na URL
     * @return ingrediente encontrado
     */
    @GetMapping("/{idIngrediente}")
    public IngredienteResponse buscarIngrediente(@PathVariable Integer idIngrediente) {
        return ingredienteService.buscar(idIngrediente);
    }

    /**
     * Cria um novo ingrediente.
     *
     * @return ingrediente criado com ID gerado
     */
    @PostMapping
    public IngredienteResponse criarIngrediente(@RequestBody IngredienteRequest request) {
        return ingredienteService.criar(request);
    }

    /**
     * Atualiza os dados de um ingrediente existente.
     * Retorna erro 404 se não existir.
     *
     * @param idIngrediente ID do ingrediente a ser atualizado
     * @return ingrediente atualizado
     */
    @PutMapping("/{idIngrediente}")
    public IngredienteResponse alterarIngrediente(
            @PathVariable Integer idIngrediente,
            @RequestBody IngredienteRequest request
    ) {
        return ingredienteService.alterar(idIngrediente, request);
    }

    /**
     * Remove um ingrediente pelo ID.
     * Retorna o ingrediente removido ou erro 404 se não existir.
     *
     * @param idIngrediente ID do ingrediente a ser removido
     * @return ingrediente removido
     */
    @DeleteMapping("/{idIngrediente}")
    public IngredienteResponse removerIngrediente(@PathVariable Integer idIngrediente) {
        return ingredienteService.remover(idIngrediente);
    }
}
