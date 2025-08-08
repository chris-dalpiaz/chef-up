package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Receitas;
import com.entra21.chef_up.repository.ReceitasRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    private final ReceitasRepository receitasRepository;

    // Construtor com injeção de dependência
    public ReceitasController(ReceitasRepository receitasRepository) {
        this.receitasRepository = receitasRepository;
    }

    /**
     * Lista todas as receitas cadastradas.
     */
    @GetMapping
    public List<Receitas> listar() {
        return receitasRepository.findAll();
    }

    /**
     * Busca uma receita específica pelo ID.
     * Retorna 404 se não for encontrada.
     */
    @GetMapping("/{idReceita}")
    public Receitas buscarReceita(@PathVariable Integer idReceita) {
        return receitasRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));
    }

    /**
     * Cria uma nova receita.
     */
    @PostMapping
    public Receitas criarReceita(@RequestBody Receitas receita) {
        return receitasRepository.save(receita);
    }

    /**
     * Atualiza os dados de uma receita existente.
     * Retorna 404 se a receita não existir.
     */
    @PutMapping("/{idReceita}")
    public Receitas alterarReceita(
            @PathVariable Integer idReceita,
            @RequestBody Receitas receita
    ) {
        Receitas alterar = receitasRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        alterar.setCategoria(receita.getCategoria());
        alterar.setNome(receita.getNome());
        alterar.setDificuldade(receita.getDificuldade());
        alterar.setDescricao(receita.getDescricao());
        alterar.setXpGanho(receita.getXpGanho());
        alterar.setTempoPreparoSegundos(receita.getTempoPreparoSegundos());

        return receitasRepository.save(alterar);
    }

    /**
     * Remove uma receita pelo ID.
     * Retorna a receita removida ou 404 se não existir.
     */
    @DeleteMapping("/{idReceita}")
    public Receitas removerReceita(@PathVariable Integer idReceita) {
        Receitas receita = receitasRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        receitasRepository.deleteById(idReceita);

        return receita;
    }
}
