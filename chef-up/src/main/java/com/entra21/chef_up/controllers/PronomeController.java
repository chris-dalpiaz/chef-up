package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.repository.PronomeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pronomes")
public class PronomeController {

    private final PronomeRepository pronomeRepository;

    // Construtor com injeção de dependência
    public PronomeController(PronomeRepository pronomeRepository) {
        this.pronomeRepository = pronomeRepository;
    }

    /**
     * Lista todos os pronomes cadastrados.
     */
    @GetMapping
    public List<Pronome> listar() {
        return pronomeRepository.findAll();
    }

    /**
     * Busca um pronome específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idPronome}")
    public Pronome buscarPronome(@PathVariable Integer idPronome) {
        return pronomeRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));
    }

    /**
     * Cria um novo pronome.
     */
    @PostMapping
    public Pronome criarPronome(@RequestBody Pronome pronome) {
        return pronomeRepository.save(pronome);
    }

    /**
     * Atualiza os dados de um pronome existente.
     * Retorna 404 se o pronome não existir.
     */
    @PutMapping("/{idPronome}")
    public Pronome alterarPronome(
            @PathVariable Integer idPronome,
            @RequestBody Pronome pronome
    ) {
        Pronome alterar = pronomeRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));

        alterar.setNome(pronome.getNome());

        return pronomeRepository.save(alterar);
    }

    /**
     * Remove um pronome pelo ID.
     * Retorna o pronome removido ou 404 se não existir.
     */
    @DeleteMapping("/{idPronome}")
    public Pronome removerPronome(@PathVariable Integer idPronome) {
        Pronome pronome = pronomeRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));

        pronomeRepository.deleteById(idPronome);

        return pronome;
    }
}
