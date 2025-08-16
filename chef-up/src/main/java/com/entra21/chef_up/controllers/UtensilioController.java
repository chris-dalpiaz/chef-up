package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Utensilio.UtensilioRequest;
import com.entra21.chef_up.dtos.Utensilio.UtensilioResponse;
import com.entra21.chef_up.services.UtensilioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações HTTP relacionadas à entidade Utensilio.
 */
@RestController
@RequestMapping("/utensilios")
public class UtensilioController {

    private final UtensilioService utensilioService;

    /// Construtor para injetar a dependência do serviço
    public UtensilioController(UtensilioService utensilioService) {
        this.utensilioService = utensilioService;
    }

    /**
     * Lista todos os utensílios cadastrados.
     *
     * @return lista de utensílios
     */
    @GetMapping
    public List<UtensilioResponse> listUtensils() {
        return utensilioService.listAll();
    }

    /**
     * Busca um utensílio específico pelo ID.
     * Retorna erro 404 se o utensílio não for encontrado.
     *
     * @param idUtensilio ID do utensílio
     * @return utensílio encontrado
     */
    @GetMapping("/{idUtensilio}")
    public UtensilioResponse getUtensil(@PathVariable Integer idUtensilio) {
        return utensilioService.getById(idUtensilio);
    }

    /**
     * Cria um novo utensílio com os dados enviados no corpo da requisição.
     *
     * @param request dados do novo utensílio
     * @return utensílio criado
     */
    @PostMapping
    public UtensilioResponse createUtensil(@RequestBody UtensilioRequest request) {
        return utensilioService.create(request);
    }

    /**
     * Atualiza os dados de um utensílio existente pelo ID.
     * Retorna erro 404 se o utensílio não existir.
     *
     * @param idUtensilio ID do utensílio
     * @param request     novos dados do utensílio
     * @return utensílio atualizado
     */
    @PutMapping("/{idUtensilio}")
    public UtensilioResponse updateUtensil(@PathVariable Integer idUtensilio,
                                           @RequestBody UtensilioRequest request) {
        return utensilioService.update(idUtensilio, request);
    }

    /**
     * Remove um utensílio pelo ID.
     * Retorna o utensílio removido ou erro 404 se não existir.
     *
     * @param idUtensilio ID do utensílio
     * @return utensílio removido
     */
    @DeleteMapping("/{idUtensilio}")
    public UtensilioResponse deleteUtensil(@PathVariable Integer idUtensilio) {
        return utensilioService.delete(idUtensilio);
    }
}
