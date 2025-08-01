package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Receitas;
import com.entra21.chef_up.entities.Titulos;
import com.entra21.chef_up.repository.TitulosRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titulos")
public class TitulosController {
    private final TitulosRepository titulosRepository;

    public TitulosController(TitulosRepository titulosRepository) {
        this.titulosRepository = titulosRepository;
    }

    @GetMapping
    public List<Titulos> listar(){
        return this.titulosRepository.findAll();
    }

    @GetMapping("/{idTitulo}")
    public Titulos buscarTitulo(@PathVariable Integer idTitulo){
        return this.titulosRepository.findById(idTitulo).get();
    }

    @PostMapping
    public Titulos criarTitulo(@RequestBody Titulos titulo) {
        this.titulosRepository.save(titulo);
        return titulo;
    }

    @PutMapping("/{idTitulo}")
    public Titulos alterarTitulo(
            @PathVariable Integer idTitulo,
            @RequestBody Titulos titulo
    ) {
        Titulos alterar = this.titulosRepository.findById(idTitulo).get();

        alterar.setNome(titulo.getNome());
        alterar.setCondicaoDesbloqueio(titulo.getCondicaoDesbloqueio());

        this.titulosRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idTitulo}")
    public Titulos removerTitulo(@PathVariable Integer idTitulo) {
        Titulos titulo = this.titulosRepository.findById(idTitulo).get();

        this.titulosRepository.deleteById(idTitulo);

        return titulo;
    }
}
