package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Categoria.CategoriaRequest;
import com.entra21.chef_up.dtos.Categoria.CategoriaResponse;
import com.entra21.chef_up.services.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return lista de objetos Categoria
     */
    @GetMapping
    public List<CategoriaResponse> listarCategorias() {
        return categoriaService.listarTodos();
    }

    /**
     * Busca uma categoria pelo ID.
     * Retorna erro 404 se não encontrada.
     *
     * @param idCategoria ID da categoria a ser buscada (na URL)
     * @return objeto Categoria encontrado
     */
    @GetMapping("/{idCategoria}")
    public CategoriaResponse buscarCategoria(@PathVariable Integer idCategoria) {
        return categoriaService.buscar(idCategoria);
    }

    /**
     * Cria uma nova categoria.
     * Recebe os dados da categoria no corpo da requisição (JSON).
     *
     * @param categoria objeto Categoria a ser criado
     * @return categoria criada com ID gerado pelo banco
     */
    @PostMapping
    public CategoriaResponse criarCategoria(@RequestBody CategoriaRequest request) {
        return categoriaService.criar(request);
    }

    /**
     * Atualiza uma categoria existente.
     * Se não encontrar pelo ID, retorna erro 404.
     *
     * @param idCategoria ID da categoria que será atualizada (URL)
     * @param categoria   novo conteúdo para atualizar a categoria (JSON no corpo)
     * @return categoria atualizada
     */
    @PutMapping("/{idCategoria}")
    public CategoriaResponse alterarCategoria(
            @PathVariable Integer idCategoria,
            @RequestBody CategoriaRequest request
    ) {
        return categoriaService.alterar(idCategoria, request);
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
    public CategoriaResponse removerCategoria(@PathVariable Integer idCategoria) {
        return categoriaService.remover(idCategoria);
    }
}
