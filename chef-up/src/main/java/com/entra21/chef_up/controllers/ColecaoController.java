package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Colecao.ColecaoRequest;
import com.entra21.chef_up.dtos.Colecao.ColecaoResponse;
import com.entra21.chef_up.dtos.ReceitaColecao.ReceitaColecaoRequest;
import com.entra21.chef_up.dtos.ReceitaColecao.ReceitaColecaoResponse;
import com.entra21.chef_up.entities.ReceitaColecao;
import com.entra21.chef_up.entities.Colecao;
import com.entra21.chef_up.repositories.ReceitaColecaoRepository;
import com.entra21.chef_up.repositories.ColecaoRepository;
import com.entra21.chef_up.services.ColecaoService;
import com.entra21.chef_up.services.ReceitaColecaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/colecoes")
public class ColecaoController {
    /**
     * Repositórios para coleções e receitas das coleções
     */
        private final ReceitaColecaoRepository receitaColecaoRepository;
        private final ColecaoService colecaoService;
        private final ReceitaColecaoService receitaColecaoService;

    /**
     * Construtor com injeção de dependência
     * Permite acessar dados de coleção e receitaColecao no banco
     */
    public ColecaoController(ColecaoRepository colecaoRepository,
                             ReceitaColecaoRepository receitaColecaoRepository, ColecaoService colecaoService, ReceitaColecaoService receitaColecaoService) {
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
    public List<ColecaoResponse> listarColecoes() {
        return colecaoService.listarTodos();
    }

    /**
     * Busca uma coleção pelo ID.
     * Retorna 404 se não encontrada.
     *
     * @param idColecao ID da coleção na URL
     * @return coleção encontrada
     */
    @GetMapping("/{idColecao}")
    public ColecaoResponse buscarColecao(@PathVariable Integer idColecao) {
        return colecaoService.buscar(idColecao);
    }

    /**
     * Cria uma nova coleção.
     *
     * @param request objeto Colecao enviado no corpo da requisição
     * @return coleção criada com ID gerado
     */
    @PostMapping
    public ColecaoResponse criarColecao(@RequestBody ColecaoRequest request) {
        return colecaoService.criar(request);
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
    public ColecaoResponse alterarColecao(
            @PathVariable Integer idColecao,
            @RequestBody ColecaoRequest request
    ) {
        return colecaoService.alterar(idColecao, request);
    }

    /**
     * Remove uma coleção pelo ID.
     * Retorna a coleção removida.
     *
     * @param idColecao ID da coleção (URL)
     * @return coleção removida
     */
    @DeleteMapping("/{idColecao}")
    public ColecaoResponse removerColecao(@PathVariable Integer idColecao) {
        return colecaoService.remover(idColecao);
    }

    ///* ---------- Receitas da coleção ---------- */
    /**
     * Lista todas as receitas associadas a uma coleção.
     *
     * @param idColecao ID da coleção
     * @return lista de receitas da coleção
     */
    @GetMapping("/{idColecao}/receitas")
    public List<ReceitaColecaoResponse> listarReceitas(@PathVariable Integer idColecao) {
        return receitaColecaoService.listarTodos();
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
    public ReceitaColecaoResponse buscarReceitaColecao(@PathVariable Integer idColecao,
                                               @PathVariable Integer idReceitaColecao) {
        return receitaColecaoService.buscar(idColecao, idReceitaColecao);
    }


    @PostMapping("/{idColecao}/receitas")
    public ReceitaColecaoResponse criarReceitaColecao(@PathVariable Integer idColecao,
                                                      @RequestBody ReceitaColecaoRequest request) {
        return receitaColecaoService.criar(idColecao, request);
    }


    /**
     * Atualiza uma receita da coleção.
     * Valida existência e pertencimento à coleção.
     *
     * @param idColecao        ID da coleção
     * @param idReceitaColecao ID da receita da coleção
     * @param request   novos dados da receita
     * @return receita atualizada
     */
    @PutMapping("/{idColecao}/receitas/{idReceitaColecao}")
    public ReceitaColecaoResponse editarReceitaColecao(@PathVariable Integer idColecao,
                                                       @PathVariable Integer idReceitaColecao,
                                                       @RequestBody ReceitaColecaoRequest request) {
        return receitaColecaoService.alterar(idColecao, idReceitaColecao, request);
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
    public ReceitaColecaoResponse removerReceitaColecao(@PathVariable Integer idColecao,
                                                @PathVariable Integer idReceitaColecao) {
        return receitaColecaoService.remover(idColecao, idReceitaColecao);
    }
}
