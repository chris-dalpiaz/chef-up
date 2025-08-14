package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Titulo.TituloRequest;
import com.entra21.chef_up.dtos.Titulo.TituloResponse;
import com.entra21.chef_up.entities.Titulo;
import com.entra21.chef_up.repositories.TituloRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações CRUD de Titulo.
 */
@Service
public class TituloService {

    private static final String ERROR_TITLE_NOT_FOUND = "Título não encontrado";

    private final TituloRepository titleRepository;
    private final ModelMapper mapper;

    public TituloService(TituloRepository titleRepository, ModelMapper mapper) {
        this.titleRepository = titleRepository;
        this.mapper = mapper;
    }

    /**
     * Retorna todos os títulos cadastrados.
     *
     * @return lista de TituloResponse
     */
    public List<TituloResponse> listAll() {
        return titleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um título pelo seu identificador.
     *
     * @param id identificador do título
     * @return DTO contendo os dados do título
     * @throws ResponseStatusException se o título não for encontrado
     */
    public TituloResponse getById(Integer id) {
        Titulo entity = findEntityById(id);
        return toResponse(entity);
    }

    /**
     * Cria um novo título com base nos dados fornecidos.
     *
     * @param request DTO contendo as informações para criação
     * @return DTO do título criado
     */
    public TituloResponse create(TituloRequest request) {
        Titulo entity = toEntity(request);
        Titulo saved = titleRepository.save(entity);
        return toResponse(saved);
    }

    /**
     * Atualiza os dados de um título existente.
     *
     * @param id      identificador do título a ser atualizado
     * @param request DTO contendo os novos dados
     * @return DTO do título atualizado
     * @throws ResponseStatusException se o título não for encontrado
     */
    public TituloResponse update(Integer id, TituloRequest request) {
        Titulo entity = findEntityById(id);
        entity.setNome(request.getNome());
        entity.setCondicaoDesbloqueio(request.getCondicaoDesbloqueio());
        Titulo updated = titleRepository.save(entity);
        return toResponse(updated);
    }

    /**
     * Remove um título pelo seu identificador.
     *
     * @param id identificador do título a ser removido
     * @return DTO do título removido
     * @throws ResponseStatusException se o título não for encontrado
     */
    public TituloResponse delete(Integer id) {
        Titulo entity = findEntityById(id);
        titleRepository.delete(entity);
        return toResponse(entity);
    }

    /**
     * Converte o DTO de requisição em entidade Titulo.
     *
     * @param request objeto de requisição
     * @return entidade Titulo mapeada
     */
    private Titulo toEntity(TituloRequest request) {
        return mapper.map(request, Titulo.class);
    }

    /**
     * Converte a entidade Titulo em DTO de resposta.
     *
     * @param titulo entidade persistida
     * @return DTO de resposta
     */
    private TituloResponse toResponse(Titulo titulo) {
        return mapper.map(titulo, TituloResponse.class);
    }

    /**
     * Busca a entidade Titulo pelo ID ou lança exceção 404.
     *
     * @param id identificador do título
     * @return entidade encontrada
     * @throws ResponseStatusException se não encontrar a entidade
     */
    private Titulo findEntityById(Integer id) {
        return titleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_TITLE_NOT_FOUND)
                );
    }
}