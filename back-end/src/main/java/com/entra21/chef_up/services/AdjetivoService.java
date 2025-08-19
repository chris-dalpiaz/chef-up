package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Adjetivo.AdjetivoRequest;
import com.entra21.chef_up.dtos.Adjetivo.AdjetivoResponse;
import com.entra21.chef_up.entities.Adjetivo;
import com.entra21.chef_up.repositories.AdjetivoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações CRUD de Adjetivo.
 */
@Service
public class AdjetivoService {

    private static final String ERROR_ADJECTIVE_NOT_FOUND = "Adjetivo não encontrado";

    private final AdjetivoRepository adjectiveRepository;
    private final ModelMapper modelMapper;

    public AdjetivoService(AdjetivoRepository adjectiveRepository, ModelMapper modelMapper) {
        this.adjectiveRepository = adjectiveRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retorna todos os adjetivos cadastrados.
     *
     * @return lista de AdjetivoResponse
     */
    public List<AdjetivoResponse> listAll() {
        return adjectiveRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um adjetivo pelo seu identificador.
     *
     * @param id identificador do adjetivo
     * @return DTO contendo os dados do adjetivo
     * @throws ResponseStatusException se o adjetivo não for encontrado
     */
    public AdjetivoResponse getById(Integer id) {
        Adjetivo adjective = findEntityById(id);
        return toResponse(adjective);
    }

    /**
     * Cria um novo adjetivo com base nos dados fornecidos.
     *
     * @param request DTO contendo as informações para criação
     * @return DTO do adjetivo criado
     */
    public AdjetivoResponse create(AdjetivoRequest request) {
        Adjetivo entity = toEntity(request);
        Adjetivo savedEntity = adjectiveRepository.save(entity);
        return toResponse(savedEntity);
    }

    /**
     * Atualiza o nome de um adjetivo existente.
     *
     * @param id      identificador do adjetivo a ser atualizado
     * @param request DTO contendo o novo nome
     * @return DTO do adjetivo atualizado
     * @throws ResponseStatusException se o adjetivo não for encontrado
     */
    public AdjetivoResponse update(Integer id, AdjetivoRequest request) {
        Adjetivo entity = findEntityById(id);
        entity.setNome(request.getNome());
        Adjetivo updatedEntity = adjectiveRepository.save(entity);
        return toResponse(updatedEntity);
    }

    /**
     * Remove um adjetivo pelo seu identificador.
     *
     * @param id identificador do adjetivo a ser removido
     * @return DTO do adjetivo removido
     * @throws ResponseStatusException se o adjetivo não for encontrado
     */
    public AdjetivoResponse delete(Integer id) {
        Adjetivo entity = findEntityById(id);
        adjectiveRepository.delete(entity);
        return toResponse(entity);
    }

    /**
     * Converte o DTO de requisição em entidade Adjetivo.
     *
     * @param request objeto de requisição
     * @return entidade Adjetivo mapeada
     */
    private Adjetivo toEntity(AdjetivoRequest request) {
        return modelMapper.map(request, Adjetivo.class);
    }

    /**
     * Converte a entidade Adjetivo em DTO de resposta.
     *
     * @param adjective entidade persistida
     * @return DTO de resposta
     */
    private AdjetivoResponse toResponse(Adjetivo adjective) {
        return modelMapper.map(adjective, AdjetivoResponse.class);
    }

    /**
     * Busca a entidade Adjetivo pelo ID ou lança exceção 404.
     *
     * @param id identificador do adjetivo
     * @return entidade encontrada
     * @throws ResponseStatusException se não encontrar a entidade
     */
    private Adjetivo findEntityById(Integer id) {
        return adjectiveRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ADJECTIVE_NOT_FOUND)
                );
    }
}