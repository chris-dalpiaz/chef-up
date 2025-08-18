package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Colecao.ColecaoRequest;
import com.entra21.chef_up.dtos.Colecao.ColecaoResponse;
import com.entra21.chef_up.dtos.ReceitaColecao.ReceitaColecaoRequest;
import com.entra21.chef_up.dtos.ReceitaColecao.ReceitaColecaoResponse;
import com.entra21.chef_up.repositories.ReceitaColecaoRepository;
import com.entra21.chef_up.repositories.ColecaoRepository;
import com.entra21.chef_up.services.ColecaoService;
import com.entra21.chef_up.services.ReceitaColecaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações HTTP relacionadas à entidade Colecao e suas receitas.
 */
@RestController
@RequestMapping("/colecoes")
public class ColecaoController {

    private final ReceitaColecaoRepository receitaColecaoRepository;
    private final ColecaoService colecaoService;
    private final ReceitaColecaoService receitaColecaoService;

    /**
     * Construtor com injeção de dependência.
     */
    public ColecaoController(ColecaoRepository colecaoRepository,
                             ReceitaColecaoRepository receitaColecaoRepository,
                             ColecaoService colecaoService,
                             ReceitaColecaoService receitaColecaoService) {
        this.receitaColecaoRepository = receitaColecaoRepository;
        this.colecaoService = colecaoService;
        this.receitaColecaoService = receitaColecaoService;
    }

    /**
     * Lista todas as coleções cadastradas.
     *
     * @return lista de coleções
     */
    @GetMapping
    public List<ColecaoResponse> listCollections() {
        return colecaoService.listAll();
    }

    /**
     * Busca uma coleção pelo ID.
     * Retorna 404 se não encontrada.
     *
     * @param idColecao ID da coleção na URL
     * @return coleção encontrada
     */
    @GetMapping("/{idColecao}")
    public ColecaoResponse getCollection(@PathVariable Integer idColecao) {
        return colecaoService.getById(idColecao);
    }

    /**
     * Cria uma nova coleção.
     *
     * @param request objeto Colecao enviado no corpo da requisição
     * @return coleção criada com ID gerado
     */
    @PostMapping
    public ColecaoResponse createCollection(@RequestBody ColecaoRequest request) {
        return colecaoService.create(request);
    }

    /**
     * Atualiza dados de uma coleção existente.
     * Retorna 404 se não encontrar.
     *
     * @param idColecao ID da coleção a ser atualizada (URL)
     * @param request   novos dados da coleção (JSON no corpo)
     * @return coleção atualizada
     */
    @PutMapping("/{idColecao}")
    public ColecaoResponse updateCollection(@PathVariable Integer idColecao,
                                            @RequestBody ColecaoRequest request) {
        return colecaoService.update(idColecao, request);
    }

    /**
     * Remove uma coleção pelo ID.
     * Retorna a coleção removida.
     *
     * @param idColecao ID da coleção (URL)
     * @return coleção removida
     */
    @DeleteMapping("/{idColecao}")
    public ColecaoResponse deleteCollection(@PathVariable Integer idColecao) {
        return colecaoService.delete(idColecao);
    }

    /* ---------- Receitas da coleção ---------- */

    /**
     * Lista todas as receitas associadas a uma coleção.
     *
     * @param idColecao ID da coleção
     * @return lista de receitas da coleção
     */
    @GetMapping("/{idColecao}/receitas")
    public List<ReceitaColecaoResponse> listCollectionRecipes(@PathVariable Integer idColecao) {
        return receitaColecaoService.listAll(idColecao);
    }

    /**
     * Busca uma receita específica dentro da coleção.
     * Retorna erro 404 se não encontrar.
     * Retorna erro 400 se a receita não pertence à coleção.
     *
     * @param idColecao        ID da coleção
     * @param idReceitaColecao ID da receita da coleção
     * @return receita da coleção encontrada
     */
    @GetMapping("/{idColecao}/receitas/{idReceitaColecao}")
    public ReceitaColecaoResponse getCollectionRecipe(@PathVariable Integer idColecao,
                                                      @PathVariable Integer idReceitaColecao) {
        return receitaColecaoService.getById(idColecao, idReceitaColecao);
    }

    /**
     * Cria uma nova associação entre receita e coleção.
     *
     * @param idColecao ID da coleção
     * @param request   dados da receita a ser associada
     * @return associação criada
     */
    @PostMapping("/{idColecao}/receitas")
    public ReceitaColecaoResponse createCollectionRecipe(@PathVariable Integer idColecao,
                                                         @RequestBody ReceitaColecaoRequest request) {
        return receitaColecaoService.create(idColecao, request);
    }

    /**
     * Atualiza uma receita da coleção.
     * Valida existência e pertencimento à coleção.
     *
     * @param idColecao        ID da coleção
     * @param idReceitaColecao ID da receita da coleção
     * @param request          novos dados da receita
     * @return receita atualizada
     */
    @PutMapping("/{idColecao}/receitas/{idReceitaColecao}")
    public ReceitaColecaoResponse updateCollectionRecipe(@PathVariable Integer idColecao,
                                                         @PathVariable Integer idReceitaColecao,
                                                         @RequestBody ReceitaColecaoRequest request) {
        return receitaColecaoService.update(idColecao, idReceitaColecao, request);
    }

    /**
     * Remove uma receita associada a uma coleção.
     * Valida existência e pertencimento.
     *
     * @param idColecao        ID da coleção
     * @param idReceitaColecao ID da receita da coleção
     * @return receita removida
     */
    @DeleteMapping("/{idColecao}/receitas/{idReceitaColecao}")
    public ReceitaColecaoResponse deleteCollectionRecipe(@PathVariable Integer idColecao,
                                                         @PathVariable Integer idReceitaColecao) {
        return receitaColecaoService.delete(idColecao, idReceitaColecao);
    }
}
