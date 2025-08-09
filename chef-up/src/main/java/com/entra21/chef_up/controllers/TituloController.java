package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Titulo;
import com.entra21.chef_up.repository.TituloRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/titulos")
public class TituloController {

    private final TituloRepository tituloRepository;

    // Construtor com injeção de dependência
    public TituloController(TituloRepository tituloRepository) {
        this.tituloRepository = tituloRepository;
    }

    /**
     * Lista todos os títulos cadastrados.
     */
    @GetMapping
    public List<Titulo> listar() {
        return tituloRepository.findAll();
    }

    /**
     * Busca um título específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idTitulo}")
    public Titulo buscarTitulo(@PathVariable Integer idTitulo) {
        return tituloRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));
    }

    /**
     * Cria um novo título.
     */
    @PostMapping
    public Titulo criarTitulo(@RequestBody Titulo titulo) {
        return tituloRepository.save(titulo);
    }

    /**
     * Atualiza os dados de um título existente.
     * Retorna 404 se o título não existir.
     */
    @PutMapping("/{idTitulo}")
    public Titulo alterarTitulo(
            @PathVariable Integer idTitulo,
            @RequestBody Titulo titulo
    ) {
        Titulo alterar = tituloRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        alterar.setNome(titulo.getNome());
        alterar.setCondicaoDesbloqueio(titulo.getCondicaoDesbloqueio());

        return tituloRepository.save(alterar);
    }

    /**
     * Remove um título pelo ID.
     * Retorna o título removido ou 404 se não existir.
     */
    @DeleteMapping("/{idTitulo}")
    public Titulo removerTitulo(@PathVariable Integer idTitulo) {
        Titulo titulo = tituloRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        tituloRepository.deleteById(idTitulo);

        return titulo;
    }
}
