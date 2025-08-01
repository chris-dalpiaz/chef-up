package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Categorias;
import com.entra21.chef_up.entities.Ingredientes;
import com.entra21.chef_up.repository.IngredientesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredientesController {
    private final IngredientesRepository ingredientesRepository;

    public IngredientesController(IngredientesRepository ingredientesRepository) {
        this.ingredientesRepository = ingredientesRepository;
    }

    @GetMapping
    public List<Ingredientes> listar(){
        return this.ingredientesRepository.findAll();
    }

    @GetMapping("/{idIngrediente}")
    public Ingredientes buscarIngrediente(@PathVariable Integer idIngrediente){
        return this.ingredientesRepository.findById(idIngrediente).get();
    }

    @PostMapping
    public Ingredientes criarIngrediente(@RequestBody Ingredientes ingrediente) {
        this.ingredientesRepository.save(ingrediente);
        return ingrediente;
    }

    @PutMapping("/{idIngrediente}")
    public Ingredientes alterarIngrediente(
            @PathVariable Integer idIngrediente,
            @RequestBody Ingredientes ingrediente
    ) {
        Ingredientes alterar = this.ingredientesRepository.findById(idIngrediente).get();

        alterar.setNome(ingrediente.getNome());
        alterar.setCategoria(ingrediente.getCategoria());
        alterar.setDicaConservacao(ingrediente.getDicaConservacao());
        alterar.setEstimativaValidade(ingrediente.getEstimativaValidade());

        this.ingredientesRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idIngrediente}")
    public Ingredientes removerIngrediente(@PathVariable Integer idIngrediente) {
        Ingredientes ingrediente = this.ingredientesRepository.findById(idIngrediente).get();

        this.ingredientesRepository.deleteById(idIngrediente);

        return ingrediente;
    }
}
