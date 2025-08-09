package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Utensilio;
import com.entra21.chef_up.repository.UtensilioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/utensilios")
public class UtensilioController {

    private final UtensilioRepository utensiliosRepository;

    // Construtor com injeção de dependência
    public UtensilioController(UtensilioRepository utensiliosRepository) {
        this.utensiliosRepository = utensiliosRepository;
    }

    /**
     * Lista todos os utensílios cadastrados.
     */
    @GetMapping
    public List<Utensilio> listar() {
        return utensiliosRepository.findAll();
    }

    /**
     * Busca um utensílio específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idUtensilio}")
    public Utensilio buscarUtensilio(@PathVariable Integer idUtensilio) {
        return utensiliosRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));
    }

    /**
     * Cria um novo utensílio.
     */
    @PostMapping
    public Utensilio criarUtensilio(@RequestBody Utensilio utensilio) {
        return utensiliosRepository.save(utensilio);
    }

    /**
     * Atualiza os dados de um utensílio existente.
     * Retorna 404 se o utensílio não existir.
     */
    @PutMapping("/{idUtensilio}")
    public Utensilio alterarUtensilio(
            @PathVariable Integer idUtensilio,
            @RequestBody Utensilio utensilio
    ) {
        Utensilio alterar = utensiliosRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        alterar.setNome(utensilio.getNome());

        return utensiliosRepository.save(alterar);
    }

    /**
     * Remove um utensílio pelo ID.
     * Retorna o utensílio removido ou 404 se não existir.
     */
    @DeleteMapping("/{idUtensilio}")
    public Utensilio removerUtensilio(@PathVariable Integer idUtensilio) {
        Utensilio utensilio = utensiliosRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        utensiliosRepository.deleteById(idUtensilio);

        return utensilio;
    }
}
