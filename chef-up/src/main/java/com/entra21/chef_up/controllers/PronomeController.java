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

    private final PronomeRepository pronomesRepository;

    // Construtor com injeção de dependência
    public PronomeController(PronomeRepository pronomesRepository) {
        this.pronomesRepository = pronomesRepository;
    }

    /**
     * Lista todos os pronomes cadastrados.
     */
    @GetMapping
    public List<Pronome> listar() {
        return pronomesRepository.findAll();
    }

    /**
     * Busca um pronome específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idPronome}")
    public Pronome buscarPronome(@PathVariable Integer idPronome) {
        return pronomesRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));
    }

    /**
     * Cria um novo pronome.
     */
    @PostMapping
    public Pronome criarPronome(@RequestBody Pronome pronome) {
        return pronomesRepository.save(pronome);
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
        Pronome alterar = pronomesRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));

        alterar.setNome(pronome.getNome());

        return pronomesRepository.save(alterar);
    }

    /**
     * Remove um pronome pelo ID.
     * Retorna o pronome removido ou 404 se não existir.
     */
    @DeleteMapping("/{idPronome}")
    public Pronome removerPronome(@PathVariable Integer idPronome) {
        Pronome pronome = pronomesRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));

        pronomesRepository.deleteById(idPronome);

        return pronome;
    }
}
