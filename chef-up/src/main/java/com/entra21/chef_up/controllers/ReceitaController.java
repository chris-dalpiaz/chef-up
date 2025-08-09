package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.repository.ReceitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaRepository receitaRepository;

    // Construtor com injeção de dependência
    public ReceitaController(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    /**
     * Lista todas as receitas cadastradas.
     */
    @GetMapping
    public List<Receita> listar() {
        return receitaRepository.findAll();
    }

    /**
     * Busca uma receita específica pelo ID.
     * Retorna 404 se não for encontrada.
     */
    @GetMapping("/{idReceita}")
    public Receita buscarReceita(@PathVariable Integer idReceita) {
        return receitaRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));
    }

    /**
     * Cria uma nova receita.
     */
    @PostMapping
    public Receita criarReceita(@RequestBody Receita receita) {
        return receitaRepository.save(receita);
    }

    /**
     * Atualiza os dados de uma receita existente.
     * Retorna 404 se a receita não existir.
     */
    @PutMapping("/{idReceita}")
    public Receita alterarReceita(
            @PathVariable Integer idReceita,
            @RequestBody Receita receita
    ) {
        Receita alterar = receitaRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        alterar.setCategoria(receita.getCategoria());
        alterar.setNome(receita.getNome());
        alterar.setDificuldade(receita.getDificuldade());
        alterar.setDescricao(receita.getDescricao());
        alterar.setXpGanho(receita.getXpGanho());
        alterar.setTempoPreparoSegundos(receita.getTempoPreparoSegundos());

        return receitaRepository.save(alterar);
    }

    /**
     * Remove uma receita pelo ID.
     * Retorna a receita removida ou 404 se não existir.
     */
    @DeleteMapping("/{idReceita}")
    public Receita removerReceita(@PathVariable Integer idReceita) {
        Receita receita = receitaRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        receitaRepository.deleteById(idReceita);

        return receita;
    }
}
