package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Titulo;
import com.entra21.chef_up.repositories.TituloRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/titulos") /// Define o caminho base das rotas deste controlador
public class TituloController {

    private final TituloRepository tituloRepository;

    /// Construtor com injeção de dependência do repositório
    public TituloController(TituloRepository tituloRepository) {
        this.tituloRepository = tituloRepository;
    }

    /**
     * Lista todos os títulos cadastrados no banco.
     */
    @GetMapping
    public List<Titulo> listarTitulos() {
        return tituloRepository.findAll();
    }

    /**
     * Busca um título pelo ID.
     * Retorna erro 404 se não encontrar.
     */
    @GetMapping("/{idTitulo}")
    public Titulo buscarTitulo(@PathVariable Integer idTitulo) {
        return tituloRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));
    }

    /**
     * Cria um novo título com os dados enviados no corpo da requisição.
     */
    @PostMapping
    public Titulo criarTitulo(@RequestBody Titulo titulo) {
        return tituloRepository.save(titulo);
    }

    /**
     * Atualiza um título existente pelo ID.
     * Se não existir, retorna erro 404.
     */
    @PutMapping("/{idTitulo}")
    public Titulo alterarTitulo(
            @PathVariable Integer idTitulo,
            @RequestBody Titulo titulo
    ) {
        /// Busca o título para alterar, ou lança erro 404
        Titulo alterar = tituloRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        /// Atualiza os campos permitidos
        alterar.setNome(titulo.getNome());
        alterar.setCondicaoDesbloqueio(titulo.getCondicaoDesbloqueio());

        /// Salva e retorna o título atualizado
        return tituloRepository.save(alterar);
    }

    /**
     * Remove um título pelo ID.
     * Retorna erro 404 se não existir.
     */
    @DeleteMapping("/{idTitulo}")
    public Titulo removerTitulo(@PathVariable Integer idTitulo) {
        /// Busca o título para remover, ou lança erro 404
        Titulo titulo = tituloRepository.findById(idTitulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        /// Remove o título do banco
        tituloRepository.deleteById(idTitulo);

        /// Retorna o título removido
        return titulo;
    }
}
