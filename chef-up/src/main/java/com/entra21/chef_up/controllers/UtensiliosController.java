package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Usuarios;
import com.entra21.chef_up.entities.Utensilios;
import com.entra21.chef_up.repository.UtensiliosRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utensilios")
public class UtensiliosController {
    private final UtensiliosRepository utensiliosRepository;

    public UtensiliosController(UtensiliosRepository utensiliosRepository) {
        this.utensiliosRepository = utensiliosRepository;
    }

    @GetMapping
    public List<Utensilios> listar(){
        return this.utensiliosRepository.findAll();
    }

    @GetMapping("/{idUtensilio}")
    public Utensilios buscarUtensilio(@PathVariable Integer idUtensilio){
        return this.utensiliosRepository.findById(idUtensilio).get();
    }

    @PostMapping
    public Utensilios criarUtensilio(@RequestBody Utensilios utensilio) {
        this.utensiliosRepository.save(utensilio);
        return utensilio;
    }

    @PutMapping("/{idUtensilio}")
    public Utensilios alterarUtensilio(
            @PathVariable Integer idUtensilio,
            @RequestBody Utensilios utensilio
    ) {
        Utensilios alterar = this.utensiliosRepository.findById(idUtensilio).get();

        alterar.setNome(utensilio.getNome());

        this.utensiliosRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idUtensilio}")
    public Utensilios removerUtensilio(@PathVariable Integer idUtensilio) {
        Utensilios utensilio = this.utensiliosRepository.findById(idUtensilio).get();

        this.utensiliosRepository.deleteById(idUtensilio);

        return utensilio;
    }
}
