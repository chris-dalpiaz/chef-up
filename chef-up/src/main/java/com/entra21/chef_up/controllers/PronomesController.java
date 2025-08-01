package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Ingredientes;
import com.entra21.chef_up.entities.Pronomes;
import com.entra21.chef_up.repository.PronomesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pronomes")
public class PronomesController {
    private final PronomesRepository pronomesRepository;

    public PronomesController(PronomesRepository pronomesRepository) {
        this.pronomesRepository = pronomesRepository;
    }

    @GetMapping
    public List<Pronomes> listar(){
        return this.pronomesRepository.findAll();
    }

    @GetMapping("/{idPronome}")
    public Pronomes buscarPronome(@PathVariable Integer idPronome){
        return this.pronomesRepository.findById(idPronome).get();
    }

    @PostMapping
    public Pronomes criarPronome(@RequestBody Pronomes pronome) {
        this.pronomesRepository.save(pronome);
        return pronome;
    }

    @PutMapping("/{idPronome}")
    public Pronomes alterarPronome(
            @PathVariable Integer idPronome,
            @RequestBody Pronomes pronome
    ) {
        Pronomes alterar = this.pronomesRepository.findById(idPronome).get();

        alterar.setNome(pronome.getNome());

        this.pronomesRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idPronome}")
    public Pronomes removerPronome(@PathVariable Integer idPronome) {
        Pronomes pronome = this.pronomesRepository.findById(idPronome).get();

        this.pronomesRepository.deleteById(idPronome);

        return pronome;
    }
}
