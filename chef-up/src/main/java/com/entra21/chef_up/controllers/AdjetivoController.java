package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Adjetivo;
import com.entra21.chef_up.repository.AdjetivoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/adjetivos")
public class AdjetivoController {

    private final AdjetivoRepository adjetivoRepository;

    // Construtor com injeção de dependência
    public AdjetivoController(AdjetivoRepository adjetivoRepository) {
        this.adjetivoRepository = adjetivoRepository;
    }

    /**
     * Lista todos os adjetivos cadastrados.
     */
    @GetMapping
    public List<Adjetivo> listar() {
        return adjetivoRepository.findAll();
    }

    /**
     * Busca um adjetivo específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idAdjetivo}")
    public Adjetivo buscarAdjetivo(@PathVariable Integer idAdjetivo) {
        return adjetivoRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));
    }

    /**
     * Cria um novo adjetivo.
     */
    @PostMapping
    public Adjetivo criarAdjetivo(@RequestBody Adjetivo adjetivo) {
        return adjetivoRepository.save(adjetivo);
    }

    /**
     * Atualiza os dados de um adjetivo existente.
     * Retorna 404 se o adjetivo não existir.
     */
    @PutMapping("/{idAdjetivo}")
    public Adjetivo alterarAdjetivo(
            @PathVariable Integer idAdjetivo,
            @RequestBody Adjetivo adjetivo
    ) {
        Adjetivo alterar = adjetivoRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        alterar.setNome(adjetivo.getNome());

        return adjetivoRepository.save(alterar);
    }

    /**
     * Remove um adjetivo pelo ID.
     * Retorna o adjetivo removido ou 404 se não existir.
     */
    @DeleteMapping("/{idAdjetivo}")
    public Adjetivo removerAdjetivo(@PathVariable Integer idAdjetivo) {
        Adjetivo adjetivo = adjetivoRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        adjetivoRepository.deleteById(idAdjetivo);

        return adjetivo;
    }
}
