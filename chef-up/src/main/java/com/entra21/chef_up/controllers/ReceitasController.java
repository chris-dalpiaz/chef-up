package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Pronomes;
import com.entra21.chef_up.entities.Receitas;
import com.entra21.chef_up.repository.ReceitasRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {
    private final ReceitasRepository receitasRepository;

    public ReceitasController(ReceitasRepository receitasRepository) {
        this.receitasRepository = receitasRepository;
    }

    @GetMapping
    public List<Receitas> listar(){
        return this.receitasRepository.findAll();
    }

    @GetMapping("/{idReceita}")
    public Receitas buscarReceita(@PathVariable Integer idReceita){
        return this.receitasRepository.findById(idReceita).get();
    }

    @PostMapping
    public Receitas criarReceita(@RequestBody Receitas receita) {
        this.receitasRepository.save(receita);
        return receita;
    }

    @PutMapping("/{idReceita}")
    public Receitas alterarReceita(
            @PathVariable Integer idReceita,
            @RequestBody Receitas receita
    ) {
        Receitas alterar = this.receitasRepository.findById(idReceita).get();

        alterar.setCategoria(receita.getCategoria());
        alterar.setNome(receita.getNome());
        alterar.setDificuldade(receita.getDificuldade());
        alterar.setDescricao(receita.getDescricao());
        alterar.setXpGanho(receita.getXpGanho());
        alterar.setTempoPreparoSegundos(receita.getTempoPreparoSegundos());

        this.receitasRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idReceita}")
    public Receitas removerReceita(@PathVariable Integer idReceita) {
        Receitas receita = this.receitasRepository.findById(idReceita).get();

        this.receitasRepository.deleteById(idReceita);

        return receita;
    }
}
