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

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    /**
     * Construtor com injeção de dependência do repositório
     * Permite a classe usar o categoriaRepository para operações CRUD
     */
    public CategoriaService(CategoriaRepository categoriaRepository,
                            ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoriaResponse> listarTodos() {
        return categoriaRepository.findAll().stream()
                .map(u -> modelMapper.map(u, CategoriaResponse.class))
                .toList();
    }

    public CategoriaResponse buscar(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Categoria não encontrada"));

        return modelMapper.map(categoria, CategoriaResponse.class);
    }

    public CategoriaResponse criar(CategoriaRequest request) {
        /// Converte o DTO de requisição para a entidade
        Categoria categoria = modelMapper.map(request, Categoria.class);

        /// Salva a entidade no banco de dados
        Categoria salvo = categoriaRepository.save(categoria);

        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, CategoriaResponse.class);
    }

    public CategoriaResponse alterar(Integer id, CategoriaRequest request) {
        /// Busca pelo ID ou lança erro 404
        Categoria alterar = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Categoria não encontrada"));

        /// Atualiza o nome com os dados do request
        alterar.setNome(request.getNome());
        alterar.setIconeUrl(request.getIconeUrl());

        /// Salva a alteração no banco
        Categoria salvo = categoriaRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, CategoriaResponse.class);
    }

    public CategoriaResponse remover(Integer id) {
        /// Busca pelo ID ou lança 404
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Categoria não encontrada"));

        /// Deleta pelo ID
        categoriaRepository.deleteById(id);

        /// Retorna o DTO do deletado
        return modelMapper.map(categoria, CategoriaResponse.class);
    }
}
