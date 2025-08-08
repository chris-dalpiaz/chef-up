package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Titulos;
import com.entra21.chef_up.repository.TitulosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/titulos")
public class TitulosController {

    private final TitulosRepository titulosRepository;

    // Construtor com injeção de dependência
    public TitulosController(TitulosRepository titulosRepository) {
        this.titulosRepository = titulosRepository;
    }

    /**
     * Lista todos os títulos cadastrados.
     */
    @GetMapping
    public List<Titulos> listar() {
        return titulosRepository.findAll();
    }

    /**
     * Busca um título específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idTitulo}")
    public Titulos buscarTitulo(@PathVariable Integer idTitulo) {
        return titulosRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));
    }

    /**
     * Cria um novo título.
     */
    @PostMapping
    public Titulos criarTitulo(@RequestBody Titulos titulo) {
        return titulosRepository.save(titulo);
    }

    /**
     * Atualiza os dados de um título existente.
     * Retorna 404 se o título não existir.
     */
    @PutMapping("/{idTitulo}")
    public Titulos alterarTitulo(
            @PathVariable Integer idTitulo,
            @RequestBody Titulos titulo
    ) {
        Titulos alterar = titulosRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        alterar.setNome(titulo.getNome());
        alterar.setCondicaoDesbloqueio(titulo.getCondicaoDesbloqueio());

        return titulosRepository.save(alterar);
    }

    /**
     * Remove um título pelo ID.
     * Retorna o título removido ou 404 se não existir.
     */
    @DeleteMapping("/{idTitulo}")
    public Titulos removerTitulo(@PathVariable Integer idTitulo) {
        Titulos titulo = titulosRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        titulosRepository.deleteById(idTitulo);

        return titulo;
    }
}
