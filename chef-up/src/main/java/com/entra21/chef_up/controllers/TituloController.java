package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Titulo.TituloRequest;
import com.entra21.chef_up.dtos.Titulo.TituloResponse;
import com.entra21.chef_up.services.TituloService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titulos")
/// Define o caminho base das rotas deste controlador
public class TituloController {

    private final TituloService tituloService;

    /// Construtor com injeção de dependência do repositório
    public TituloController(TituloService tituloService) {
        this.tituloService = tituloService;
    }

    /**
     * Lista todos os títulos cadastrados no banco.
     */
    @GetMapping
    public List<TituloResponse> listarTitulos() {
        return tituloService.listarTodos();
    }

    /**
     * Busca um título pelo ID.
     * Retorna erro 404 se não encontrar.
     */
    @GetMapping("/{idTitulo}")
    public TituloResponse buscarTitulo(@PathVariable Integer idTitulo) {
        return tituloService.buscar(idTitulo);
    }

    /**
     * Cria um novo título com os dados enviados no corpo da requisição.
     */
    @PostMapping
    public TituloResponse criarTitulo(@RequestBody TituloRequest request) {
        return tituloService.criar(request);
    }

    /**
     * Atualiza um título existente pelo ID.
     * Se não existir, retorna erro 404.
     */
    @PutMapping("/{idTitulo}")
    public TituloResponse alterarTitulo(
            @PathVariable Integer idTitulo,
            @RequestBody TituloRequest request
    ) {
        return tituloService.alterar(idTitulo, request);
    }

    /**
     * Remove um título pelo ID.
     * Retorna erro 404 se não existir.
     */
    @DeleteMapping("/{idTitulo}")
    public TituloResponse removerTitulo(@PathVariable Integer idTitulo) {
        return tituloService.remover(idTitulo);
    }
}
