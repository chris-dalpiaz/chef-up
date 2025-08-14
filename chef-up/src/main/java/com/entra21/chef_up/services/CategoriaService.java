package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Categoria.CategoriaRequest;
import com.entra21.chef_up.dtos.Categoria.CategoriaResponse;
import com.entra21.chef_up.entities.Categoria;
import com.entra21.chef_up.repositories.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações de CRUD para Categoria.
 */
@Service
public class CategoriaService {

    private static final String ERROR_CATEGORY_NOT_FOUND = "Categoria não encontrada";

    private final CategoriaRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoriaService(CategoriaRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as categorias cadastradas.
     *
     * @return lista de CategoriaResponse
     */
    public List<CategoriaResponse> listAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> mapper.map(category, CategoriaResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca uma categoria pelo seu ID.
     *
     * @param id identificador da categoria
     * @return DTO da categoria encontrada
     */
    public CategoriaResponse getById(Integer id) {
        Categoria category = findByIdOrThrow(id);
        return mapper.map(category, CategoriaResponse.class);
    }

    /**
     * Cria uma nova categoria.
     *
     * @param request DTO com os dados da categoria
     * @return DTO da categoria criada
     */
    public CategoriaResponse create(CategoriaRequest request) {
        Categoria newCategory = mapper.map(request, Categoria.class);
        Categoria saved = categoryRepository.save(newCategory);
        return mapper.map(saved, CategoriaResponse.class);
    }

    /**
     * Atualiza uma categoria existente.
     *
     * @param id      identificador da categoria
     * @param request DTO com os dados atualizados
     * @return DTO da categoria atualizada
     */
    public CategoriaResponse update(Integer id, CategoriaRequest request) {
        Categoria existing = findByIdOrThrow(id);

        existing.setNome(request.getNome());
        existing.setIconeUrl(request.getIconeUrl());

        Categoria updated = categoryRepository.save(existing);
        return mapper.map(updated, CategoriaResponse.class);
    }

    /**
     * Remove uma categoria pelo ID.
     *
     * @param id identificador da categoria
     * @return DTO da categoria removida
     */
    public CategoriaResponse delete(Integer id) {
        Categoria category = findByIdOrThrow(id);
        categoryRepository.deleteById(id);
        return mapper.map(category, CategoriaResponse.class);
    }

    /**
     * Busca uma entidade Categoria pelo ID ou lança exceção 404.
     *
     * @param idCategoria identificador da categoria
     * @return entidade Categoria
     */
    public Categoria findByIdOrThrow(Integer idCategoria) {
        return categoryRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_CATEGORY_NOT_FOUND));
    }

    /**
     * Converte uma entidade Categoria para seu DTO de resposta.
     *
     * @param category entidade Categoria
     * @return DTO CategoriaResponse
     */
    public CategoriaResponse toResponse(Categoria category) {
        return mapper.map(category, CategoriaResponse.class);
    }
}
