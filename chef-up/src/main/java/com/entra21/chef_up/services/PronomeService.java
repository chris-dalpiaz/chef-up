package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Pronome.PronomeRequest;
import com.entra21.chef_up.dtos.Pronome.PronomeResponse;
import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.repositories.PronomeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações CRUD de pronome.
 */
@Service
public class PronomeService {

    private static final String ERROR_PRONOUN_NOT_FOUND = "Pronome não encontrado";

    private final PronomeRepository pronounRepository;
    private final ModelMapper mapper;

    public PronomeService(PronomeRepository pronounRepository,
                          ModelMapper mapper) {
        this.pronounRepository = pronounRepository;
        this.mapper = mapper;
    }

    /**
     * Retorna todos os pronomes cadastrados.
     *
     * @return lista de PronomeResponse
     */
    public List<PronomeResponse> listAll() {
        return pronounRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um pronome pelo seu identificador.
     *
     * @param id identificador do pronome
     * @return DTO contendo os dados do pronome
     * @throws ResponseStatusException se o pronome não for encontrado
     */
    public PronomeResponse getById(Integer id) {
        Pronome pronoun = findEntityById(id);
        return toResponse(pronoun);
    }

    /**
     * Cria um novo pronome com base nos dados fornecidos.
     *
     * @param request DTO contendo as informações para criação
     * @return DTO do pronome criado
     */
    public PronomeResponse create(PronomeRequest request) {
        Pronome entity = toEntity(request);
        Pronome saved = pronounRepository.save(entity);
        return toResponse(saved);
    }

    /**
     * Atualiza o nome de um pronome existente.
     *
     * @param id      identificador do pronome a ser atualizado
     * @param request DTO contendo o novo nome
     * @return DTO do pronome atualizado
     * @throws ResponseStatusException se o pronome não for encontrado
     */
    public PronomeResponse update(Integer id, PronomeRequest request) {
        Pronome entity = findEntityById(id);
        entity.setNome(request.getNome());
        Pronome updated = pronounRepository.save(entity);
        return toResponse(updated);
    }

    /**
     * Remove um pronome pelo seu identificador.
     *
     * @param id identificador do pronome a ser removido
     * @return DTO do pronome removido
     * @throws ResponseStatusException se o pronome não for encontrado
     */
    public PronomeResponse delete(Integer id) {
        Pronome entity = findEntityById(id);
        pronounRepository.delete(entity);
        return toResponse(entity);
    }

    /**
     * Retorna a entidade Pronome pelo ID ou lança exceção 404.
     *
     * @param id identificador do pronome
     * @return entidade Pronome
     * @throws ResponseStatusException se o pronome não for encontrado
     */
    public Pronome findEntityById(Integer id) {
        return pronounRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_PRONOUN_NOT_FOUND)
                );
    }

    /**
     * Converte o DTO de requisição em entidade Pronome.
     *
     * @param request objeto de requisição
     * @return entidade Pronome mapeada
     */
    private Pronome toEntity(PronomeRequest request) {
        return mapper.map(request, Pronome.class);
    }

    /**
     * Converte a entidade Pronome em DTO de resposta.
     *
     * @param pronoun entidade persistida
     * @return DTO de resposta
     */
    public PronomeResponse toResponse(Pronome pronoun) {
        return mapper.map(pronoun, PronomeResponse.class);
    }
}
