package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Adjetivos;
import com.entra21.chef_up.repository.AdjetivosRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adjetivos")
public class AdjetivosController {
    private final AdjetivosRepository adjetivosRepository;

    public AdjetivosController(AdjetivosRepository adjetivosRepository) {
        this.adjetivosRepository = adjetivosRepository;
    }

    @GetMapping
    public List<Adjetivos> listar(){
        return this.adjetivosRepository.findAll();
    }

    @GetMapping("/{idAdjetivo}")
    public Adjetivos buscarCarro(@PathVariable Integer idAdjetivo){
        return this.adjetivosRepository.findById(idAdjetivo).get();
    }

    @PostMapping
    public Adjetivos criarAdjetivo(@RequestBody Adjetivos adjetivo) {
        this.adjetivosRepository.save(adjetivo);
        return adjetivo;
    }

    @PutMapping("/{idAdjetivo}")
    public Adjetivos alterarAdjetivo(
            @PathVariable Integer idAdjetivo,
            @RequestBody Adjetivos adjetivo
    ) {
        Adjetivos alterar = this.adjetivosRepository.findById(idAdjetivo).get();

        alterar.setNome(adjetivo.getNome());

        this.adjetivosRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idAdjetivo}")
    public Adjetivos removerAdjetivo(@PathVariable Integer idAdjetivo) {
        Adjetivos adjetivo = this.adjetivosRepository.findById(idAdjetivo).get();

        this.adjetivosRepository.deleteById(idAdjetivo);

        return adjetivo;
    }
}
