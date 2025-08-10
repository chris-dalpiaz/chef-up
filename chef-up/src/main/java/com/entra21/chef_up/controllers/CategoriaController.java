package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Categoria;
import com.entra21.chef_up.repositories.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    /** Repositório para acesso ao banco de dados das categorias */
    private final CategoriaRepository categoriaRepository;

    /**
     * Construtor com injeção de dependência do repositório
     * Permite a classe usar o categoriaRepository para operações CRUD
     */
    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Lista todas as categorias cadastradas no sistema.
     *
     * @return lista de objetos Categoria
     */
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    /**
     * Busca uma categoria pelo ID.
     * Retorna erro 404 se não encontrada.
     *
     * @param idCategoria ID da categoria a ser buscada (na URL)
     * @return objeto Categoria encontrado
     */
    @GetMapping("/{idCategoria}")
    public Categoria buscarCategoria(@PathVariable Integer idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }

    /**
     * Cria uma nova categoria.
     * Recebe os dados da categoria no corpo da requisição (JSON).
     *
     * @param categoria objeto Categoria a ser criado
     * @return categoria criada com ID gerado pelo banco
     */
    @PostMapping
    public Categoria criarCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /**
     * Atualiza uma categoria existente.
     * Se não encontrar pelo ID, retorna erro 404.
     *
     * @param idCategoria ID da categoria que será atualizada (URL)
     * @param categoria novo conteúdo para atualizar a categoria (JSON no corpo)
     * @return categoria atualizada
     */
    @PutMapping("/{idCategoria}")
    public Categoria alterarCategoria(
            @PathVariable Integer idCategoria,
            @RequestBody Categoria categoria
    ) {
        /// Busca a categoria para atualização
        Categoria alterar = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        /// Atualiza os campos permitidos
        alterar.setNome(categoria.getNome());
        alterar.setIconeUrl(categoria.getIconeUrl());

        /// Salva as alterações no banco
        return categoriaRepository.save(alterar);
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
    public Categoria removerCategoria(@PathVariable Integer idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        /// Deleta a categoria do banco
        categoriaRepository.deleteById(idCategoria);

        /// Retorna a categoria excluída
        return categoria;
    }
}
