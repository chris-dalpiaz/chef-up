package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Utensilio.UtensilioRequest;
import com.entra21.chef_up.dtos.Utensilio.UtensilioResponse;
import com.entra21.chef_up.entities.Utensilio;
import com.entra21.chef_up.repositories.UtensilioRepository;
import com.entra21.chef_up.services.UtensilioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/utensilios") /// Define o caminho base para as rotas deste controlador
public class UtensilioController {

    private final UtensilioService utensilioService;

    /// Construtor para injetar a dependência do repositório
    public UtensilioController(UtensilioService utensilioService) {
        this.utensilioService = utensilioService;
    }

    /**
     * Lista todos os utensílios cadastrados.
     */
    @GetMapping
    public List<UtensilioResponse> listarUtensilios() {
        return utensilioService.listarTodos();
    }

    /**
     * Busca um utensílio específico pelo ID.
     * Retorna erro 404 se o utensílio não for encontrado.
     */
    @GetMapping("/{idUtensilio}")
    public UtensilioResponse buscarUtensilio(@PathVariable Integer idUtensilio) {
        return utensilioService.buscar(idUtensilio);
    }

    /**
     * Cria um novo utensílio com os dados enviados no corpo da requisição.
     */
    @PostMapping
    public UtensilioResponse criarUtensilio(@RequestBody UtensilioRequest request) {
        return utensilioService.criar(request);
    }

    /**
     * Atualiza os dados de um utensílio existente pelo ID.
     * Retorna erro 404 se o utensílio não existir.
     */
    @PutMapping("/{idUtensilio}")
    public UtensilioResponse alterarUtensilio(
            @PathVariable Integer idUtensilio,
            @RequestBody UtensilioRequest request
    ) {
        return utensilioService.alterar(idUtensilio, request);
    }

    /**
     * Remove um utensílio pelo ID.
     * Retorna o utensílio removido ou erro 404 se não existir.
     */
    @DeleteMapping("/{idUtensilio}")
    public UtensilioResponse removerUtensilio(@PathVariable Integer idUtensilio) {
        return utensilioService.remover(idUtensilio);
    }
}
