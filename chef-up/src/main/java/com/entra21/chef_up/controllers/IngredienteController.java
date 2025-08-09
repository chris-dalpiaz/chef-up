package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.repository.IngredienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteRepository ingredientesRepository;

    // Construtor com injeção de dependência
    public IngredienteController(IngredienteRepository ingredientesRepository) {
        this.ingredientesRepository = ingredientesRepository;
    }

    /**
     * Lista todos os ingredientes cadastrados.
     */
    @GetMapping
    public List<Ingrediente> listar() {
        return ingredientesRepository.findAll();
    }

    /**
     * Busca um ingrediente específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idIngrediente}")
    public Ingrediente buscarIngrediente(@PathVariable Integer idIngrediente) {
        return ingredientesRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));
    }

    /**
     * Cria um novo ingrediente.
     */
    @PostMapping
    public Ingrediente criarIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredientesRepository.save(ingrediente);
    }

    /**
     * Atualiza os dados de um ingrediente existente.
     * Retorna 404 se o ingrediente não existir.
     */
    @PutMapping("/{idIngrediente}")
    public Ingrediente alterarIngrediente(
            @PathVariable Integer idIngrediente,
            @RequestBody Ingrediente ingrediente
    ) {
        Ingrediente alterar = ingredientesRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        alterar.setNome(ingrediente.getNome());
        alterar.setCategoria(ingrediente.getCategoria());
        alterar.setDicaConservacao(ingrediente.getDicaConservacao());
        alterar.setEstimativaValidade(ingrediente.getEstimativaValidade());

        return ingredientesRepository.save(alterar);
    }

    /**
     * Remove um ingrediente pelo ID.
     * Retorna o ingrediente removido ou 404 se não existir.
     */
    @DeleteMapping("/{idIngrediente}")
    public Ingrediente removerIngrediente(@PathVariable Integer idIngrediente) {
        Ingrediente ingrediente = ingredientesRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        ingredientesRepository.deleteById(idIngrediente);

        return ingrediente;
    }
}
