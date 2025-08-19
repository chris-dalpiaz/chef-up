package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Pronome.PronomeRequest;
import com.entra21.chef_up.dtos.Pronome.PronomeResponse;
import com.entra21.chef_up.services.PronomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações HTTP relacionadas à entidade Pronome.
 */
@RestController
@RequestMapping("/pronomes")
public class PronomeController {

    private final PronomeService pronomeService;

    /**
     * Construtor com injeção de dependência do serviço de pronome.
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
    public List<PronomeResponse> listPronouns() {
        return pronomeService.listAll();
    }

    /**
     * Busca um pronome pelo ID.
     * Retorna erro 404 se não encontrado.
     *
     * @param idPronome ID do pronome na URL
     * @return pronome encontrado
     */
    @GetMapping("/{idPronome}")
    public PronomeResponse getPronoun(@PathVariable Integer idPronome) {
        return pronomeService.getById(idPronome);
    }

    /**
     * Cria um novo pronome.
     *
     * @param request dados do novo pronome
     * @return pronome criado com ID gerado
     */
    @PostMapping
    public PronomeResponse createPronoun(@RequestBody PronomeRequest request) {
        return pronomeService.create(request);
    }

    /**
     * Atualiza um pronome existente.
     * Retorna erro 404 se o pronome não existir.
     *
     * @param idPronome ID do pronome a ser atualizado
     * @param request   novos dados do pronome
     * @return pronome atualizado
     */
    @PutMapping("/{idPronome}")
    public PronomeResponse updatePronoun(@PathVariable Integer idPronome,
                                         @RequestBody PronomeRequest request) {
        return pronomeService.update(idPronome, request);
    }

    /**
     * Remove um pronome pelo ID.
     * Retorna o pronome removido ou erro 404 se não existir.
     *
     * @param idPronome ID do pronome a ser removido
     * @return pronome removido
     */
    @DeleteMapping("/{idPronome}")
    public PronomeResponse deletePronoun(@PathVariable Integer idPronome) {
        return pronomeService.delete(idPronome);
    }
}
