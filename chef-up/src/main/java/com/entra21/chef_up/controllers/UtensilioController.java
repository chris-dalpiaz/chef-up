package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Utensilio;
import com.entra21.chef_up.repositories.UtensilioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/utensilios") /// Define o caminho base para as rotas deste controlador
public class UtensilioController {

    private final UtensilioRepository utensilioRepository;

    /// Construtor para injetar a dependência do repositório
    public UtensilioController(UtensilioRepository utensilioRepository) {
        this.utensilioRepository = utensilioRepository;
    }

    /**
     * Lista todos os utensílios cadastrados.
     */
    @GetMapping
    public List<Utensilio> listarUtensilios() {
        return utensilioRepository.findAll();
    }

    /**
     * Busca um utensílio específico pelo ID.
     * Retorna erro 404 se o utensílio não for encontrado.
     */
    @GetMapping("/{idUtensilio}")
    public Utensilio buscarUtensilio(@PathVariable Integer idUtensilio) {
        return utensilioRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));
    }

    /**
     * Cria um novo utensílio com os dados enviados no corpo da requisição.
     */
    @PostMapping
    public Utensilio criarUtensilio(@RequestBody Utensilio utensilio) {
        return utensilioRepository.save(utensilio);
    }

    /**
     * Atualiza os dados de um utensílio existente pelo ID.
     * Retorna erro 404 se o utensílio não existir.
     */
    @PutMapping("/{idUtensilio}")
    public Utensilio alterarUtensilio(
            @PathVariable Integer idUtensilio,
            @RequestBody Utensilio utensilio
    ) {
        /// Busca o utensílio para alterar, ou lança erro 404 se não encontrado
        Utensilio alterar = utensilioRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        /// Atualiza o nome do utensílio
        alterar.setNome(utensilio.getNome());

        /// Salva as alterações e retorna o utensílio atualizado
        return utensilioRepository.save(alterar);
    }

    /**
     * Remove um utensílio pelo ID.
     * Retorna o utensílio removido ou erro 404 se não existir.
     */
    @DeleteMapping("/{idUtensilio}")
    public Utensilio removerUtensilio(@PathVariable Integer idUtensilio) {
        /// Busca o utensílio para remover, ou lança erro 404 se não encontrado
        Utensilio utensilio = utensilioRepository.findById(idUtensilio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        /// Remove o utensílio do banco de dados
        utensilioRepository.deleteById(idUtensilio);

        /// Retorna o utensílio removido
        return utensilio;
    }
}
