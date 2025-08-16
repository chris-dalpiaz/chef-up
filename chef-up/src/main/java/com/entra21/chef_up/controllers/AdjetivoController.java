package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Adjetivo.AdjetivoRequest;
import com.entra21.chef_up.dtos.Adjetivo.AdjetivoResponse;
import com.entra21.chef_up.services.AdjetivoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller responsável pelas operações HTTP relacionadas à entidade Adjetivo.
 */
@RestController
@RequestMapping("/adjetivos")
public class AdjetivoController {

    private final AdjetivoService adjetivoService;

    /**
     * Construtor com injeção do serviço de adjetivo.
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
    public List<AdjetivoResponse> listAdjectives() {
        return adjetivoService.listAll();
    }

    /**
     * Busca um adjetivo específico pelo ID.
     *
     * @param idAdjetivo id do adjetivo a ser buscado
     * @return o adjetivo encontrado
     * @throws ResponseStatusException 404 se não encontrar o adjetivo
     */
    @GetMapping("/{idAdjetivo}")
    public AdjetivoResponse getAdjective(@PathVariable Integer idAdjetivo) {
        return adjetivoService.getById(idAdjetivo);
    }

    /**
     * Cria um novo adjetivo no banco.
     *
     * @param request dados do novo adjetivo
     * @return adjetivo criado
     */
    @PostMapping
    public AdjetivoResponse createAdjective(@RequestBody AdjetivoRequest request) {
        return adjetivoService.create(request);
    }

    /**
     * Atualiza os dados de um adjetivo existente pelo ID.
     *
     * @param idAdjetivo id do adjetivo a ser alterado
     * @param request    novos dados do adjetivo
     * @return adjetivo atualizado
     */
    @PutMapping("/{idAdjetivo}")
    public AdjetivoResponse updateAdjective(@PathVariable Integer idAdjetivo,
                                            @RequestBody AdjetivoRequest request) {
        return adjetivoService.update(idAdjetivo, request);
    }

    /**
     * Remove um adjetivo pelo ID.
     *
     * @param idAdjetivo id do adjetivo a ser removido
     * @return o adjetivo removido
     * @throws ResponseStatusException 404 se não encontrar o adjetivo
     */
    @DeleteMapping("/{idAdjetivo}")
    public AdjetivoResponse deleteAdjective(@PathVariable Integer idAdjetivo) {
        return adjetivoService.delete(idAdjetivo);
    }
}
