package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Categoria.CategoriaRequest;
import com.entra21.chef_up.dtos.Categoria.CategoriaResponse;
import com.entra21.chef_up.services.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações HTTP relacionadas à entidade Categoria.
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Lista todas as categorias cadastradas no sistema.
     *
     * @return lista de objetos CategoriaResponse
     */
    @GetMapping
    public List<CategoriaResponse> listCategories() {
        return categoriaService.listAll();
    }

    /**
     * Busca uma categoria pelo ID.
     * Retorna erro 404 se não encontrada.
     *
     * @param idCategoria ID da categoria a ser buscada (na URL)
     * @return objeto CategoriaResponse encontrado
     */
    @GetMapping("/{idCategoria}")
    public CategoriaResponse getCategory(@PathVariable Integer idCategoria) {
        return categoriaService.getById(idCategoria);
    }

    /**
     * Cria uma nova categoria.
     * Recebe os dados da categoria no corpo da requisição (JSON).
     *
     * @param request objeto CategoriaRequest a ser criado
     * @return categoria criada com ID gerado pelo banco
     */
    @PostMapping
    public CategoriaResponse createCategory(@RequestBody CategoriaRequest request) {
        return categoriaService.create(request);
    }

    /**
     * Atualiza uma categoria existente.
     * Se não encontrar pelo ID, retorna erro 404.
     *
     * @param idCategoria ID da categoria que será atualizada (URL)
     * @param request     novo conteúdo para atualizar a categoria (JSON no corpo)
     * @return categoria atualizada
     */
    @PutMapping("/{idCategoria}")
    public CategoriaResponse updateCategory(@PathVariable Integer idCategoria,
                                            @RequestBody CategoriaRequest request) {
        return categoriaService.update(idCategoria, request);
    }

    /**
     * Remove uma categoria pelo ID.
     * Retorna a categoria removida para confirmação.
     * Se não encontrar, retorna erro 404.
     *
     * @param idCategoria ID da categoria a ser removida (URL)
     * @return categoria removida
     */
    @DeleteMapping("/{idCategoria}")
    public CategoriaResponse deleteCategory(@PathVariable Integer idCategoria) {
        return categoriaService.delete(idCategoria);
    }
}
