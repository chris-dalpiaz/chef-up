package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Pronomes;
import com.entra21.chef_up.repository.PronomesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pronomes")
public class PronomesController {

    private final PronomesRepository pronomesRepository;

    // Construtor com injeção de dependência
    public PronomesController(PronomesRepository pronomesRepository) {
        this.pronomesRepository = pronomesRepository;
    }

    /**
     * Lista todos os pronomes cadastrados.
     */
    @GetMapping
    public List<Pronomes> listar() {
        return pronomesRepository.findAll();
    }

    /**
     * Busca um pronome específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idPronome}")
    public Pronomes buscarPronome(@PathVariable Integer idPronome) {
        return pronomesRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));
    }

    /**
     * Cria um novo pronome.
     */
    @PostMapping
    public Pronomes criarPronome(@RequestBody Pronomes pronome) {
        return pronomesRepository.save(pronome);
    }

    /**
     * Atualiza os dados de um pronome existente.
     * Retorna 404 se o pronome não existir.
     */
    @PutMapping("/{idPronome}")
    public Pronomes alterarPronome(
            @PathVariable Integer idPronome,
            @RequestBody Pronomes pronome
    ) {
        Pronomes alterar = pronomesRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));

        alterar.setNome(pronome.getNome());

        return pronomesRepository.save(alterar);
    }

    /**
     * Remove um pronome pelo ID.
     * Retorna o pronome removido ou 404 se não existir.
     */
    @DeleteMapping("/{idPronome}")
    public Pronomes removerPronome(@PathVariable Integer idPronome) {
        Pronomes pronome = pronomesRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));

        pronomesRepository.deleteById(idPronome);

        return pronome;
    }
}
