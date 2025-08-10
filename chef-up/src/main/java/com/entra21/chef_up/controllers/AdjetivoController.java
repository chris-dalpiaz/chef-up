package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Adjetivo;
import com.entra21.chef_up.repositories.AdjetivoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/adjetivos")
public class AdjetivoController {

    /**
     * Repositório para operações CRUD com a entidade Adjetivo no banco de dados
     */
    private final AdjetivoRepository adjetivoRepository;

    /**
     * Construtor com injeção do repositório via dependência.
     * Permite usar o repositório para acessar os dados.
     */
    public AdjetivoController(AdjetivoRepository adjetivoRepository) {
        this.adjetivoRepository = adjetivoRepository;
    }

    /**
     * Lista todos os adjetivos cadastrados.
     *
     * @return lista com todos os adjetivos encontrados no banco
     */
    @GetMapping
    public List<Adjetivo> listarAdjetivos() {
        return adjetivoRepository.findAll();
    }

    /**
     * Busca um adjetivo específico pelo ID.
     *
     * @param idAdjetivo id do adjetivo a ser buscado
     * @return o adjetivo encontrado
     * @throws ResponseStatusException 404 se não encontrar o adjetivo
     */
    @GetMapping("/{idAdjetivo}")
    public Adjetivo buscarAdjetivo(@PathVariable Integer idAdjetivo) {
        return adjetivoRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));
    }

    /**
     * Cria um novo adjetivo no banco.
     *
     * @param adjetivo objeto Adjetivo vindo no corpo da requisição (JSON)
     * @return o adjetivo salvo com ID gerado
     */
    @PostMapping
    public Adjetivo criarAdjetivo(@RequestBody Adjetivo adjetivo) {
        return adjetivoRepository.save(adjetivo);
    }

    /**
     * Atualiza os dados de um adjetivo existente pelo ID.
     *
     * @param idAdjetivo id do adjetivo que será alterado
     * @param adjetivo objeto com os novos dados para atualizar
     * @return o adjetivo atualizado
     * @throws ResponseStatusException 404 se não encontrar o adjetivo
     */
    @PutMapping("/{idAdjetivo}")
    public Adjetivo alterarAdjetivo(
            @PathVariable Integer idAdjetivo,
            @RequestBody Adjetivo adjetivo
    ) {
        Adjetivo alterar = adjetivoRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        alterar.setNome(adjetivo.getNome());

        return adjetivoRepository.save(alterar);
    }

    /**
     * Remove um adjetivo pelo ID.
     *
     * @param idAdjetivo id do adjetivo a ser removido
     * @return o adjetivo removido
     * @throws ResponseStatusException 404 se não encontrar o adjetivo
     */
    @DeleteMapping("/{idAdjetivo}")
    public Adjetivo removerAdjetivo(@PathVariable Integer idAdjetivo) {
        Adjetivo adjetivo = adjetivoRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        adjetivoRepository.deleteById(idAdjetivo);

        return adjetivo;
    }
}
