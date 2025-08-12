package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Pronome.PronomeRequest;
import com.entra21.chef_up.dtos.Pronome.PronomeResponse;
import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.repositories.PronomeRepository;
import com.entra21.chef_up.services.PronomeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pronomes")
public class PronomeController {

    /**
     * Repositório para manipular dados de pronomes
     */
    private final PronomeService pronomeService;

    /**
     * Construtor com injeção de dependência do repositório.
     */
    public PronomeController(PronomeService pronomeService) {
        this.pronomeService = pronomeService;
    }

    /**
     * Lista todos os pronomes cadastrados.
     *
     * @return lista com todos os pronomes do banco
     */
    @GetMapping
    public List<PronomeResponse> listarPronomes() {
        return pronomeService.listarTodos();
    }

    /**
     * Busca um pronome pelo ID.
     * Retorna erro 404 se não encontrado.
     *
     * @param idPronome ID do pronome na URL
     * @return pronome encontrado
     */
    @GetMapping("/{idPronome}")
    public PronomeResponse buscarPronome(@PathVariable Integer idPronome) {
        return pronomeService.buscar(idPronome);
    }

    /**
     * Cria um novo pronome.
     *
     * @return pronome criado com ID gerado
     */
    @PostMapping
    public PronomeResponse criarPronome(@RequestBody PronomeRequest request) {
        return pronomeService.criar(request);
    }

    /**
     * Atualiza um pronome existente.
     * Retorna erro 404 se o pronome não existir.
     *
     * @param idPronome ID do pronome a ser atualizado
     * @return pronome atualizado
     */
    @PutMapping("/{idPronome}")
    public PronomeResponse alterarPronome(
            @PathVariable Integer idPronome,
            @RequestBody PronomeRequest request
    ) {
        return pronomeService.alterar(idPronome, request);
    }

    /**
     * Remove um pronome pelo ID.
     * Retorna o pronome removido ou erro 404 se não existir.
     *
     * @param idPronome ID do pronome a ser removido
     * @return pronome removido
     */
    @DeleteMapping("/{idPronome}")
    public PronomeResponse removerPronome(@PathVariable Integer idPronome) {
        return pronomeService.remover(idPronome);
    }
}
