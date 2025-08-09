package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Categoria;
import com.entra21.chef_up.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriasRepository;

    // Construtor com injeção de dependência
    public CategoriaController(CategoriaRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    /**
     * Lista todas as categorias cadastradas.
     */
    @GetMapping
    public List<Categoria> listar() {
        return categoriasRepository.findAll();
    }

    /**
     * Busca uma categoria específica pelo ID.
     * Retorna 404 se não for encontrada.
     */
    @GetMapping("/{idCategoria}")
    public Categoria buscarCategoria(@PathVariable Integer idCategoria) {
        return categoriasRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }

    /**
     * Cria uma nova categoria.
     */
    @PostMapping
    public Categoria criarCategoria(@RequestBody Categoria categoria) {
        return categoriasRepository.save(categoria);
    }

    /**
     * Atualiza os dados de uma categoria existente.
     * Retorna 404 se a categoria não existir.
     */
    @PutMapping("/{idCategoria}")
    public Categoria alterarCategoria(
            @PathVariable Integer idCategoria,
            @RequestBody Categoria categoria
    ) {
        Categoria alterar = categoriasRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        alterar.setNome(categoria.getNome());
        alterar.setIconeUrl(categoria.getIconeUrl());

        return categoriasRepository.save(alterar);
    }

    /**
     * Remove uma categoria pelo ID.
     * Retorna a categoria removida ou 404 se não existir.
     */
    @DeleteMapping("/{idCategoria}")
    public Categoria removerCategoria(@PathVariable Integer idCategoria) {
        Categoria categoria = categoriasRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        categoriasRepository.deleteById(idCategoria);

        return categoria;
    }
}
