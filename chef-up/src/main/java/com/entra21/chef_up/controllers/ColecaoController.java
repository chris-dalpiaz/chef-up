package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.ReceitaColecao;
import com.entra21.chef_up.entities.Colecao;
import com.entra21.chef_up.repository.ReceitaColecaoRepository;
import com.entra21.chef_up.repository.ColecaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/colecoes")
public class ColecaoController {
    private final ColecaoRepository colecaoRepository;
    private final ReceitaColecaoRepository receitaColecaoRepository;

    public ColecaoController(ColecaoRepository colecaoRepository, ReceitaColecaoRepository receitaColecaoRepository) {
        this.colecaoRepository = colecaoRepository;
        this.receitaColecaoRepository = receitaColecaoRepository;
    }

    /**
     * Lista todos as coleções cadastrados.
     */
    @GetMapping
    public List<Colecao> listar() {
        return colecaoRepository.findAll();
    }

    /**
     * Busca uma coleção específico pelo ID.
     * Retorna 404 se não for encontrada.
     */
    @GetMapping("/{idColecao}")
    public Colecao buscarColecao(@PathVariable Integer idColecao) {
        return colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));
    }

    /**
     * Cria uma nova coleção.
     */
    @PostMapping
    public Colecao criarColecao(@RequestBody Colecao colecao) {
        return colecaoRepository.save(colecao);
    }

    /**
     * Atualiza os dados de uma coleção existente.
     * Retorna 404 se a coleção não existir.
     */
    @PutMapping("/{idColecao}")
    public Colecao alterarColecao(
            @PathVariable Integer idColecao,
            @RequestBody Colecao colecao
    ) {
        Colecao alterar = colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));

        alterar.setNome(colecao.getNome());
        alterar.setUsuario(colecao.getUsuario());

        return colecaoRepository.save(alterar);
    }

    /**
     * Remove uma coleção pelo ID.
     * Retorna a coleção removida ou 404 se não existir.
     */
    @DeleteMapping("/{idColecao}")
    public Colecao removerColecao(@PathVariable Integer idColecao) {
        Colecao colecao = colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));

        colecaoRepository.deleteById(idColecao);

        return colecao;
    }

    // Receitas da coleção

    // Lista todas as receitas associadas a uma coleção
    @GetMapping("/{idColecao}/receitas")
    public List<ReceitaColecao> listarReceitas(@PathVariable Integer idColecao) {
        return receitaColecaoRepository.findByColecaoId(idColecao);
    }

    // Busca uma receita específica da coleção
    @GetMapping("/{idColecao}/receitas/{idReceita}")
    public ReceitaColecao buscarReceitaColecao(@PathVariable Integer idColecao,
                                               @PathVariable Integer idReceita) {

        ReceitaColecao receitaColecao = receitaColecaoRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita da coleção não encontrada"));

        if (!receitaColecao.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }

        return receitaColecao;
    }


    // Cria uma nova receita que pertence a coleção
    @PostMapping("/{idColecao}/receitas")
    public ReceitaColecao criarReceitaColecao(@PathVariable Integer idColecao,
                                              @RequestBody ReceitaColecao receitaColecao) {

        Colecao colecao = colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));

        receitaColecao.setColecao(colecao);

        return receitaColecaoRepository.save(receitaColecao);
    }

    // Edita uma receita de uma coleção
    @PutMapping("/{idColecao}/receitas/{idReceita}")
    public ReceitaColecao editarReceitaColecao(@PathVariable Integer idColecao,
                                               @PathVariable Integer idReceita,
                                               @RequestBody ReceitaColecao receitaColecao) {

        ReceitaColecao alterar = receitaColecaoRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        if (!alterar.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }

        alterar.setReceita(receitaColecao.getReceita());

        return receitaColecaoRepository.save(alterar);
    }

    // Remove uma receita associada a uma coleção
    @DeleteMapping("/{idColecao}/receitas/{idReceita}")
    public ReceitaColecao removerReceitaColecao(@PathVariable Integer idColecao,
                                                @PathVariable Integer idReceita) {

        ReceitaColecao receita = receitaColecaoRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        if (!receita.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }

        receitaColecaoRepository.delete(receita);
        return receita;
    }
}
