package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Utensilio.UtensilioRequest;
import com.entra21.chef_up.dtos.Utensilio.UtensilioResponse;
import com.entra21.chef_up.entities.Utensilio;
import com.entra21.chef_up.repositories.UtensilioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações CRUD de Utensilio.
 */
@Service
public class UtensilioService {

    private static final String ERROR_UTENSIL_NOT_FOUND = "Utensílio não encontrado";

    private final UtensilioRepository utensilRepository;
    private final ModelMapper modelMapper;

    public UtensilioService(UtensilioRepository utensilRepository,
                            ModelMapper modelMapper) {
        this.utensilRepository = utensilRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retorna todos os utensílios cadastrados.
     *
     * @return lista de UtensilioResponse
     */
    public List<UtensilioResponse> listAll() {
        return utensilRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um utensílio pelo seu identificador.
     *
     * @param id identificador do utensílio
     * @return DTO contendo os dados do utensílio
     * @throws ResponseStatusException se o utensílio não for encontrado
     */
    public UtensilioResponse getById(Integer id) {
        Utensilio utensil = findEntityById(id);
        return toResponse(utensil);
    }

    /**
     * Cria um novo utensílio com base nos dados fornecidos.
     *
     * @param request DTO contendo as informações para criação
     * @return DTO do utensílio criado
     */
    public UtensilioResponse create(UtensilioRequest request) {
        Utensilio entity = toEntity(request);
        Utensilio saved = utensilRepository.save(entity);
        return toResponse(saved);
    }

    /**
     * Atualiza o nome de um utensílio existente.
     *
     * @param id      identificador do utensílio a ser atualizado
     * @param request DTO contendo o novo nome
     * @return DTO do utensílio atualizado
     * @throws ResponseStatusException se o utensílio não for encontrado
     */
    public UtensilioResponse update(Integer id, UtensilioRequest request) {
        Utensilio entity = findEntityById(id);
        entity.setNome(request.getNome());
        Utensilio updated = utensilRepository.save(entity);
        return toResponse(updated);
    }

    /**
     * Remove um utensílio pelo seu identificador.
     *
     * @param id identificador do utensílio a ser removido
     * @return DTO do utensílio removido
     * @throws ResponseStatusException se o utensílio não for encontrado
     */
    public UtensilioResponse delete(Integer id) {
        Utensilio entity = findEntityById(id);
        utensilRepository.delete(entity);
        return toResponse(entity);
    }

    /**
     * Converte o DTO de requisição em entidade Utensilio.
     *
     * @param request objeto de requisição
     * @return entidade Utensilio mapeada
     */
    private Utensilio toEntity(UtensilioRequest request) {
        return modelMapper.map(request, Utensilio.class);
    }

    /**
     * Converte a entidade Utensilio em DTO de resposta.
     *
     * @param utensil entidade persistida
     * @return DTO de resposta
     */
    private UtensilioResponse toResponse(Utensilio utensil) {
        return modelMapper.map(utensil, UtensilioResponse.class);
    }

    /**
     * Busca a entidade Utensilio pelo ID ou lança exceção 404.
     *
     * @param id identificador do utensílio
     * @return entidade encontrada
     * @throws ResponseStatusException se não encontrar a entidade
     */
    private Utensilio findEntityById(Integer id) {
        return utensilRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_UTENSIL_NOT_FOUND)
                );
    }
}
