package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Utensilios;
import com.entra21.chef_up.repository.UtensiliosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/utensilios")
public class UtensiliosController {

    private final UtensiliosRepository utensiliosRepository;

    // Construtor com injeção de dependência
    public UtensiliosController(UtensiliosRepository utensiliosRepository) {
        this.utensiliosRepository = utensiliosRepository;
    }

    /**
     * Lista todos os utensílios cadastrados.
     */
    @GetMapping
    public List<Utensilios> listar() {
        return utensiliosRepository.findAll();
    }

    /**
     * Busca um utensílio específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idUtensilio}")
    public Utensilios buscarUtensilio(@PathVariable Integer idUtensilio) {
        return utensiliosRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));
    }

    /**
     * Cria um novo utensílio.
     */
    @PostMapping
    public Utensilios criarUtensilio(@RequestBody Utensilios utensilio) {
        return utensiliosRepository.save(utensilio);
    }

    /**
     * Atualiza os dados de um utensílio existente.
     * Retorna 404 se o utensílio não existir.
     */
    @PutMapping("/{idUtensilio}")
    public Utensilios alterarUtensilio(
            @PathVariable Integer idUtensilio,
            @RequestBody Utensilios utensilio
    ) {
        Utensilios alterar = utensiliosRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        alterar.setNome(utensilio.getNome());

        return utensiliosRepository.save(alterar);
    }

    /**
     * Remove um utensílio pelo ID.
     * Retorna o utensílio removido ou 404 se não existir.
     */
    @DeleteMapping("/{idUtensilio}")
    public Utensilios removerUtensilio(@PathVariable Integer idUtensilio) {
        Utensilios utensilio = utensiliosRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        utensiliosRepository.deleteById(idUtensilio);

        return utensilio;
    }
}
