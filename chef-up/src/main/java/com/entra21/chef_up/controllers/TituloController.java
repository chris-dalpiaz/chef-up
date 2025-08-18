package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Titulo.TituloRequest;
import com.entra21.chef_up.dtos.Titulo.TituloResponse;
import com.entra21.chef_up.services.TituloService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações HTTP relacionadas à entidade Titulo.
 */
@RestController
@RequestMapping("/titulos")
public class TituloController {

    private final TituloService tituloService;

    /// Construtor com injeção de dependência do serviço
    public TituloController(TituloService tituloService) {
        this.tituloService = tituloService;
    }

    /**
     * Lista todos os títulos cadastrados no banco.
     *
     * @return lista de títulos
     */
    @GetMapping
    public List<TituloResponse> listTitles() {
        return tituloService.listAll();
    }

    /**
     * Busca um título pelo ID.
     * Retorna erro 404 se não encontrar.
     *
     * @param idTitulo ID do título
     * @return título encontrado
     */
    @GetMapping("/{idTitulo}")
    public TituloResponse getTitle(@PathVariable Integer idTitulo) {
        return tituloService.getById(idTitulo);
    }

    /**
     * Cria um novo título com os dados enviados no corpo da requisição.
     *
     * @param request dados do novo título
     * @return título criado
     */
    @PostMapping
    public TituloResponse createTitle(@RequestBody TituloRequest request) {
        return tituloService.create(request);
    }

    /**
     * Atualiza um título existente pelo ID.
     * Se não existir, retorna erro 404.
     *
     * @param idTitulo ID do título
     * @param request  novos dados do título
     * @return título atualizado
     */
    @PutMapping("/{idTitulo}")
    public TituloResponse updateTitle(@PathVariable Integer idTitulo,
                                      @RequestBody TituloRequest request) {
        return tituloService.update(idTitulo, request);
    }

    /**
     * Remove um título pelo ID.
     * Retorna erro 404 se não existir.
     *
     * @param idTitulo ID do título
     * @return título removido
     */
    @DeleteMapping("/{idTitulo}")
    public TituloResponse deleteTitle(@PathVariable Integer idTitulo) {
        return tituloService.delete(idTitulo);
    }
}
