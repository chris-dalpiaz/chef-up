package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Adjetivo.AdjetivoRequest;
import com.entra21.chef_up.dtos.Adjetivo.AdjetivoResponse;
import com.entra21.chef_up.services.AdjetivoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/adjetivos")
public class AdjetivoController {

    /**
     * Repositório para operações CRUD com a entidade Adjetivo no banco de dados
     */
    private final AdjetivoService adjetivoService;

    /**
     * Construtor com injeção do repositório via dependência.
     * Permite usar o repositório para acessar os dados.
     */
    public AdjetivoController(AdjetivoService adjetivoService) {
        this.adjetivoService = adjetivoService;
    }

    /**
     * Lista todos os adjetivos cadastrados.
     *
     * @return lista com todos os adjetivos encontrados no banco
     */
    @GetMapping
    public List<AdjetivoResponse> listarAdjetivos() {
        return adjetivoService.listarTodos();
    }

    /**
     * Busca um adjetivo específico pelo ID.
     *
     * @param idAdjetivo id do adjetivo a ser buscado
     * @return o adjetivo encontrado
     * @throws ResponseStatusException 404 se não encontrar o adjetivo
     */
    @GetMapping("/{idAdjetivo}")
    public AdjetivoResponse buscarAdjetivo(@PathVariable Integer idAdjetivo) {
        return adjetivoService.buscar(idAdjetivo);
    }

    /**
     * Cria um novo adjetivo no banco.
     */
    @PostMapping
    public AdjetivoResponse criarAdjetivo(@RequestBody AdjetivoRequest request) {
        return adjetivoService.criar(request);
    }

    /**
     * Atualiza os dados de um adjetivo existente pelo ID.
     */
    @PutMapping("/{idAdjetivo}")
    public AdjetivoResponse alterarAdjetivo(
            @PathVariable Integer idAdjetivo,
            @RequestBody AdjetivoRequest request
    ) {
        return adjetivoService.alterar(idAdjetivo, request);
    }

    /**
     * Remove um adjetivo pelo ID.
     *
     * @param idAdjetivo id do adjetivo a ser removido
     * @return o adjetivo removido
     * @throws ResponseStatusException 404 se não encontrar o adjetivo
     */
    @DeleteMapping("/{idAdjetivo}")
    public AdjetivoResponse removerAdjetivo(@PathVariable Integer idAdjetivo) {
        return adjetivoService.remover(idAdjetivo);
    }
}