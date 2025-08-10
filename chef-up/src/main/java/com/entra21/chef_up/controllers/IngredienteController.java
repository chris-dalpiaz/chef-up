package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.repositories.IngredienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    /** Repositório para acesso aos dados dos ingredientes */
    private final IngredienteRepository ingredienteRepository;

    /**
     * Construtor com injeção de dependência.
     * Recebe o repositório para manipular ingredientes.
     */
    public IngredienteController(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    /**
     * Lista todos os ingredientes cadastrados.
     *
     * @return lista com todos ingredientes no banco
     */
    @GetMapping
    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }

    /**
     * Busca um ingrediente pelo ID.
     * Retorna erro 404 se não encontrado.
     *
     * @param idIngrediente ID do ingrediente na URL
     * @return ingrediente encontrado
     */
    @GetMapping("/{idIngrediente}")
    public Ingrediente buscarIngrediente(@PathVariable Integer idIngrediente) {
        return ingredienteRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));
    }

    /**
     * Cria um novo ingrediente.
     *
     * @param ingrediente dados do ingrediente enviados no corpo JSON
     * @return ingrediente criado com ID gerado
     */
    @PostMapping
    public Ingrediente criarIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    /**
     * Atualiza os dados de um ingrediente existente.
     * Retorna erro 404 se não existir.
     *
     * @param idIngrediente ID do ingrediente a ser atualizado
     * @param ingrediente novos dados do ingrediente no corpo da requisição
     * @return ingrediente atualizado
     */
    @PutMapping("/{idIngrediente}")
    public Ingrediente alterarIngrediente(
            @PathVariable Integer idIngrediente,
            @RequestBody Ingrediente ingrediente
    ) {
        Ingrediente alterar = ingredienteRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        alterar.setNome(ingrediente.getNome());
        alterar.setCategoria(ingrediente.getCategoria());
        alterar.setDicaConservacao(ingrediente.getDicaConservacao());
        alterar.setEstimativaValidade(ingrediente.getEstimativaValidade());

        return ingredienteRepository.save(alterar);
    }

    /**
     * Remove um ingrediente pelo ID.
     * Retorna o ingrediente removido ou erro 404 se não existir.
     *
     * @param idIngrediente ID do ingrediente a ser removido
     * @return ingrediente removido
     */
    @DeleteMapping("/{idIngrediente}")
    public Ingrediente removerIngrediente(@PathVariable Integer idIngrediente) {
        Ingrediente ingrediente = ingredienteRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        ingredienteRepository.deleteById(idIngrediente);

        return ingrediente;
    }
}
