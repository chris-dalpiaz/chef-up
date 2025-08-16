package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Colecao.ColecaoRequest;
import com.entra21.chef_up.dtos.Colecao.ColecaoResponse;
import com.entra21.chef_up.entities.Colecao;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.ColecaoRepository;
import com.entra21.chef_up.repositories.ReceitaColecaoRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações de CRUD para Colecao.
 */
@Service
public class ColecaoService {

    private static final String ERROR_COLLECTION_NOT_FOUND = "Coleção não encontrada";
    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado";

    private final UsuarioRepository userRepository;
    private final ReceitaColecaoRepository recipeCollectionRepository;
    private final ColecaoRepository collectionRepository;
    private final ModelMapper mapper;

    public ColecaoService(UsuarioRepository userRepository,
                          ReceitaColecaoRepository recipeCollectionRepository,
                          ColecaoRepository collectionRepository,
                          ModelMapper mapper) {
        this.userRepository = userRepository;
        this.recipeCollectionRepository = recipeCollectionRepository;
        this.collectionRepository = collectionRepository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as coleções cadastradas.
     *
     * @return lista de ColecaoResponse
     */
    public List<ColecaoResponse> listAll() {
        return collectionRepository.findAll()
                .stream()
                .map(collection -> mapper.map(collection, ColecaoResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca uma coleção pelo seu ID.
     *
     * @param id identificador da coleção
     * @return DTO da coleção encontrada
     */
    public ColecaoResponse getById(Integer id) {
        Colecao collection = findByIdOrThrow(id);
        return mapper.map(collection, ColecaoResponse.class);
    }

    /**
     * Cria uma nova coleção associada a um usuário.
     *
     * @param request DTO com os dados da coleção
     * @return DTO da coleção criada
     */
    public ColecaoResponse create(ColecaoRequest request) {
        Usuario user = userRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));

        Colecao newCollection = new Colecao();
        newCollection.setNome(request.getNome());
        newCollection.setUsuario(user);

        Colecao saved = collectionRepository.save(newCollection);
        return mapper.map(saved, ColecaoResponse.class);
    }

    /**
     * Atualiza uma coleção existente.
     *
     * @param id      identificador da coleção
     * @param request DTO com os dados atualizados
     * @return DTO da coleção atualizada
     */
    public ColecaoResponse update(Integer id, ColecaoRequest request) {
        Colecao collection = findByIdOrThrow(id);
        collection.setNome(request.getNome());

        Colecao updated = collectionRepository.save(collection);
        return mapper.map(updated, ColecaoResponse.class);
    }

    /**
     * Remove uma coleção e suas dependências.
     *
     * @param id identificador da coleção
     * @return DTO da coleção removida
     */
    @Transactional
    public ColecaoResponse delete(Integer id) {
        Colecao collection = findByIdOrThrow(id);

        recipeCollectionRepository.removeByColecaoId(id);
        collectionRepository.deleteById(id);

        return mapper.map(collection, ColecaoResponse.class);
    }

    /**
     * Busca uma coleção ou lança exceção 404.
     *
     * @param id identificador
     * @return entidade Colecao
     */
    private Colecao findByIdOrThrow(Integer id) {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_COLLECTION_NOT_FOUND));
    }
}
