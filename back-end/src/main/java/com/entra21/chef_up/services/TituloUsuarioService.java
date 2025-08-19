package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.TituloUsuario.TituloUsuarioRequest;
import com.entra21.chef_up.dtos.TituloUsuario.TituloUsuarioResponse;
import com.entra21.chef_up.entities.Titulo;
import com.entra21.chef_up.entities.TituloUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.TituloRepository;
import com.entra21.chef_up.repositories.TituloUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar a relação entre Titulo e Usuario.
 */
@Service
public class TituloUsuarioService {

    private static final String ERROR_ASSOCIATION_NOT_FOUND = "Associação não encontrada";
    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado";
    private static final String ERROR_TITLE_NOT_FOUND = "Título não encontrado";
    private static final String ERROR_TITLE_NOT_OWNED = "Título não pertence ao usuário";

    private final TituloRepository titleRepository;
    private final UsuarioRepository userRepository;
    private final TituloUsuarioRepository titleUserRepository;
    private final ModelMapper mapper;

    public TituloUsuarioService(TituloRepository titleRepository,
                                UsuarioRepository userRepository,
                                TituloUsuarioRepository titleUserRepository,
                                ModelMapper mapper) {
        this.titleRepository = titleRepository;
        this.userRepository = userRepository;
        this.titleUserRepository = titleUserRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as associações entre título e usuário para um determinado usuário.
     *
     * @param userId identificador do usuário
     * @return lista de TituloUsuarioResponse
     */
    public List<TituloUsuarioResponse> listAll(Integer userId) {
        return titleUserRepository.findByUsuarioId(userId)
                .stream()
                .map(association -> mapper.map(association, TituloUsuarioResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera uma associação específica entre título e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação encontrada
     */
    public TituloUsuarioResponse getById(Integer userId, Integer associationId) {
        TituloUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_TITLE_NOT_OWNED);
        }

        return mapper.map(association, TituloUsuarioResponse.class);
    }

    /**
     * Cria uma nova associação entre título e usuário.
     *
     * @param userId  identificador do usuário
     * @param request DTO com o ID do título
     * @return DTO da associação criada
     */
    public TituloUsuarioResponse create(Integer userId, TituloUsuarioRequest request) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));

        Titulo title = titleRepository.findById(request.getIdTitulo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_TITLE_NOT_FOUND));

        TituloUsuario newAssociation = new TituloUsuario();
        newAssociation.setUsuario(user);
        newAssociation.setTitulo(title);
        newAssociation.setDesbloqueadoEm(LocalDateTime.now());
        newAssociation.setEstaAtivo(false);

        TituloUsuario saved = titleUserRepository.save(newAssociation);
        return mapper.map(saved, TituloUsuarioResponse.class);
    }

    /**
     * Atualiza uma associação existente entre título e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @param request       DTO com o novo ID do título
     * @return DTO da associação atualizada
     */
    public TituloUsuarioResponse update(Integer userId, Integer associationId, TituloUsuarioRequest request) {
        TituloUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_TITLE_NOT_OWNED);
        }

        if (request.getIdTitulo() != null && !request.getIdTitulo().equals(association.getTitulo().getId())) {
            Titulo newTitle = titleRepository.findById(request.getIdTitulo())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo título não encontrado"));
            association.setTitulo(newTitle);
        }

        // Verifica se o campo estaAtivo foi informado e atualiza
        if (request.getEstaAtivo() != null) {
            association.setEstaAtivo(request.getEstaAtivo());
        }

        TituloUsuario updated = titleUserRepository.save(association);
        return mapper.map(updated, TituloUsuarioResponse.class);
    }

    /**
     * Remove uma associação entre título e usuário.
     *
     * @param userId        identificador do usuário
     * @param associationId identificador da associação
     * @return DTO da associação removida
     */
    @Transactional
    public TituloUsuarioResponse delete(Integer userId, Integer associationId) {
        TituloUsuario association = findByIdOrThrow(associationId);

        if (!association.getUsuario().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_TITLE_NOT_OWNED);
        }

        titleUserRepository.deleteById(associationId);
        return mapper.map(association, TituloUsuarioResponse.class);
    }

    /**
     * Busca uma associação entre título e usuário ou lança exceção 404.
     *
     * @param id identificador da associação
     * @return entidade TituloUsuario
     */
    private TituloUsuario findByIdOrThrow(Integer id) {
        return titleUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ASSOCIATION_NOT_FOUND));
    }

    public TituloUsuarioResponse toResponse(TituloUsuario userTitle) {
        return mapper.map(userTitle, TituloUsuarioResponse.class);
    }

    public List<TituloUsuarioResponse> toResponseList(List<TituloUsuario> titles) {
        return titles.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
