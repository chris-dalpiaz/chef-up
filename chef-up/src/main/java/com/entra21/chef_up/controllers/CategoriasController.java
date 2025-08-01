package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Avatares;
import com.entra21.chef_up.entities.Categorias;
import com.entra21.chef_up.repository.CategoriasRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {
    private final CategoriasRepository categoriasRepository;

    public CategoriasController(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    @GetMapping
    public List<Categorias> listar(){
        return this.categoriasRepository.findAll();
    }

    @GetMapping("/{idCategoria}")
    public Categorias buscarCategoria(@PathVariable Integer idCategoria){
        return this.categoriasRepository.findById(idCategoria).get();
    }

    @PostMapping
    public Categorias criarCategoria(@RequestBody Categorias categoria) {
        this.categoriasRepository.save(categoria);
        return categoria;
    }

    @PutMapping("/{idCategoria}")
    public Categorias alterarCategoria(
            @PathVariable Integer idCategoria,
            @RequestBody Categorias categoria
    ) {
        Categorias alterar = this.categoriasRepository.findById(idCategoria).get();

        alterar.setNome(categoria.getNome());
        alterar.setIconeUrl(categoria.getIconeUrl());

        this.categoriasRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idCategoria}")
    public Categorias removerCategoria(@PathVariable Integer idCategoria) {
        Categorias categoria = this.categoriasRepository.findById(idCategoria).get();

        this.categoriasRepository.deleteById(idCategoria);

        return categoria;
    }
}
