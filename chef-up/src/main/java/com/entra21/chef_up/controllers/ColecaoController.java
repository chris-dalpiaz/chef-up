package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.ReceitaColecao;
import com.entra21.chef_up.entities.Colecao;
import com.entra21.chef_up.repositories.ReceitaColecaoRepository;
import com.entra21.chef_up.repositories.ColecaoRepository;
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
    private final ColecaoRepository colecaoRepository;
    private final ReceitaColecaoRepository receitaColecaoRepository;

    /**
     * Construtor com injeção de dependência
     * Permite acessar dados de coleção e receitaColecao no banco
     */
    public ColecaoController(ColecaoRepository colecaoRepository,
                             ReceitaColecaoRepository receitaColecaoRepository) {
        this.colecaoRepository = colecaoRepository;
        this.receitaColecaoRepository = receitaColecaoRepository;
    }

    /**
     * Lista todas as coleções cadastradas.
     *
     * @return lista de coleções
     */
    @GetMapping
    public List<Colecao> listarColecoes() {
        return colecaoRepository.findAll();
    }

    /**
     * Busca uma coleção pelo ID.
     * Retorna 404 se não encontrada.
     *
     * @param idColecao ID da coleção na URL
     * @return coleção encontrada
     */
    @GetMapping("/{idColecao}")
    public Colecao buscarColecao(@PathVariable Integer idColecao) {
        return colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));
    }

    /**
     * Cria uma nova coleção.
     *
     * @param colecao objeto Colecao enviado no corpo da requisição
     * @return coleção criada com ID gerado
     */
    @PostMapping
    public Colecao criarColecao(@RequestBody Colecao colecao) {
        return colecaoRepository.save(colecao);
    }

    /**
     * Atualiza dados de uma coleção existente.
     * Retorna 404 se não encontrar.
     *
     * @param idColecao ID da coleção a ser atualizada (URL)
     * @param colecao   novos dados da coleção (JSON no corpo)
     * @return coleção atualizada
     */
    @PutMapping("/{idColecao}")
    public Colecao alterarColecao(
            @PathVariable Integer idColecao,
            @RequestBody Colecao colecao
    ) {
        /// Busca coleção existente
        Colecao alterar = colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));
        /// Atualiza campos
        alterar.setNome(colecao.getNome());
        alterar.setUsuario(colecao.getUsuario());
        /// Salva as mudanças
        return colecaoRepository.save(alterar);
    }

    /**
     * Remove uma coleção pelo ID.
     * Retorna a coleção removida.
     *
     * @param idColecao ID da coleção (URL)
     * @return coleção removida
     */
    @DeleteMapping("/{idColecao}")
    public Colecao removerColecao(@PathVariable Integer idColecao) {
        Colecao colecao = colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));
        colecaoRepository.deleteById(idColecao);
        return colecao;
    }
    ///* ---------- Receitas da coleção ---------- */
    /**
     * Lista todas as receitas associadas a uma coleção.
     *
     * @param idColecao ID da coleção
     * @return lista de receitas da coleção
     */
    @GetMapping("/{idColecao}/receitas")
    public List<ReceitaColecao> listarReceitas(@PathVariable Integer idColecao) {
        return receitaColecaoRepository.findByColecaoId(idColecao);
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
    public ReceitaColecao buscarReceitaColecao(@PathVariable Integer idColecao,
                                               @PathVariable Integer idReceitaColecao) {
        ReceitaColecao receitaColecao = receitaColecaoRepository.findById(idReceitaColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita da coleção não encontrada"));

        if (!receitaColecao.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }

        return receitaColecao;
    }

    /**
     * Cria uma nova receita vinculada à coleção.
     *
     * @param idColecao      ID da coleção
     * @param receitaColecao objeto ReceitaColecao a ser salvo
     * @return receitaColecao salva
     */
    @PostMapping("/{idColecao}/receitas")
    public ReceitaColecao criarReceitaColecao(@PathVariable Integer idColecao,
                                              @RequestBody ReceitaColecao receitaColecao) {
        Colecao colecao = colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));

        receitaColecao.setColecao(colecao);
        return receitaColecaoRepository.save(receitaColecao);
    }

    /**
     * Atualiza uma receita da coleção.
     * Valida existência e pertencimento à coleção.
     *
     * @param idColecao        ID da coleção
     * @param idReceitaColecao ID da receita da coleção
     * @param receitaColecao   novos dados da receita
     * @return receita atualizada
     */
    @PutMapping("/{idColecao}/receitas/{idReceitaColecao}")
    public ReceitaColecao editarReceitaColecao(@PathVariable Integer idColecao,
                                               @PathVariable Integer idReceitaColecao,
                                               @RequestBody ReceitaColecao receitaColecao) {
        ReceitaColecao alterar = receitaColecaoRepository.findById(idReceitaColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        if (!alterar.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }

        alterar.setReceita(receitaColecao.getReceita());
        return receitaColecaoRepository.save(alterar);
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
    public ReceitaColecao removerReceitaColecao(@PathVariable Integer idColecao,
                                                @PathVariable Integer idReceitaColecao) {
        ReceitaColecao receita = receitaColecaoRepository.findById(idReceitaColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        if (!receita.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }
        receitaColecaoRepository.delete(receita);
        return receita;
    }
}
