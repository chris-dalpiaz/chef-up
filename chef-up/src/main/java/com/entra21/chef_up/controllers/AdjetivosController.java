package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Adjetivos;
import com.entra21.chef_up.repository.AdjetivosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/adjetivos")
public class AdjetivosController {

    private final AdjetivosRepository adjetivosRepository;

    // Construtor com injeção de dependência
    public AdjetivosController(AdjetivosRepository adjetivosRepository) {
        this.adjetivosRepository = adjetivosRepository;
    }

    /**
     * Lista todos os adjetivos cadastrados.
     */
    @GetMapping
    public List<Adjetivos> listar() {
        return adjetivosRepository.findAll();
    }

    /**
     * Busca um adjetivo específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idAdjetivo}")
    public Adjetivos buscarAdjetivo(@PathVariable Integer idAdjetivo) {
        return adjetivosRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));
    }

    /**
     * Cria um novo adjetivo.
     */
    @PostMapping
    public Adjetivos criarAdjetivo(@RequestBody Adjetivos adjetivo) {
        return adjetivosRepository.save(adjetivo);
    }

    /**
     * Atualiza os dados de um adjetivo existente.
     * Retorna 404 se o adjetivo não existir.
     */
    @PutMapping("/{idAdjetivo}")
    public Adjetivos alterarAdjetivo(
            @PathVariable Integer idAdjetivo,
            @RequestBody Adjetivos adjetivo
    ) {
        Adjetivos alterar = adjetivosRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        alterar.setNome(adjetivo.getNome());

        return adjetivosRepository.save(alterar);
    }

    /**
     * Remove um adjetivo pelo ID.
     * Retorna o adjetivo removido ou 404 se não existir.
     */
    @DeleteMapping("/{idAdjetivo}")
    public Adjetivos removerAdjetivo(@PathVariable Integer idAdjetivo) {
        Adjetivos adjetivo = adjetivosRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        adjetivosRepository.deleteById(idAdjetivo);

        return adjetivo;
    }
}
