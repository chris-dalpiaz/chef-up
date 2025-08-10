package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.repositories.PronomeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pronomes")
public class PronomeController {

    /** Repositório para manipular dados de pronomes */
    private final PronomeRepository pronomeRepository;

    /**
     * Construtor com injeção de dependência do repositório.
     */
    public PronomeController(PronomeRepository pronomeRepository) {
        this.pronomeRepository = pronomeRepository;
    }

    /**
     * Lista todos os pronomes cadastrados.
     *
     * @return lista com todos os pronomes do banco
     */
    @GetMapping
    public List<Pronome> listarPronomes() {
        return pronomeRepository.findAll();
    }

    /**
     * Busca um pronome pelo ID.
     * Retorna erro 404 se não encontrado.
     *
     * @param idPronome ID do pronome na URL
     * @return pronome encontrado
     */
    @GetMapping("/{idPronome}")
    public Pronome buscarPronome(@PathVariable Integer idPronome) {
        return pronomeRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));
    }

    /**
     * Cria um novo pronome.
     *
     * @param pronome dados do pronome enviados no corpo JSON
     * @return pronome criado com ID gerado
     */
    @PostMapping
    public Pronome criarPronome(@RequestBody Pronome pronome) {
        return pronomeRepository.save(pronome);
    }

    /**
     * Atualiza um pronome existente.
     * Retorna erro 404 se o pronome não existir.
     *
     * @param idPronome ID do pronome a ser atualizado
     * @param pronome novos dados do pronome no corpo da requisição
     * @return pronome atualizado
     */
    @PutMapping("/{idPronome}")
    public Pronome alterarPronome(
            @PathVariable Integer idPronome,
            @RequestBody Pronome pronome
    ) {
        Pronome alterar = pronomeRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));

        alterar.setNome(pronome.getNome());

        return pronomeRepository.save(alterar);
    }

    /**
     * Remove um pronome pelo ID.
     * Retorna o pronome removido ou erro 404 se não existir.
     *
     * @param idPronome ID do pronome a ser removido
     * @return pronome removido
     */
    @DeleteMapping("/{idPronome}")
    public Pronome removerPronome(@PathVariable Integer idPronome) {
        Pronome pronome = pronomeRepository.findById(idPronome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pronome não encontrado"));

        pronomeRepository.deleteById(idPronome);

        return pronome;
    }
}
