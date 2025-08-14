package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.entities.ProgressoUsuario;
import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações CRUD de usuário.
 */
@Service
public class UsuarioService {

    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado";

    private final UsuarioRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final PronomeService pronounService;

    public UsuarioService(UsuarioRepository userRepository,
                          ModelMapper modelMapper,
                          PasswordEncoder passwordEncoder,
                          PronomeService pronounService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.pronounService = pronounService;
    }

    /**
     * Retorna todos os usuários cadastrados, mapeando também seus pronomes.
     *
     * @return lista de UsuarioResponse
     */
    public List<UsuarioResponse> listAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um usuário pelo seu identificador.
     *
     * @param id identificador do usuário
     * @return DTO contendo os dados do usuário
     * @throws ResponseStatusException se o usuário não for encontrado
     */
    public UsuarioResponse getById(Integer id) {
        Usuario user = findEntityById(id);
        return toResponse(user);
    }

    /**
     * Cria um novo usuário, inicializa seu progresso e criptografa a senha.
     *
     * @param request DTO contendo os dados de criação
     * @return DTO do usuário criado
     */
    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        Usuario user = toEntity(request);
        user.setDataCadastro(LocalDateTime.now());
        user.setSenhaHash(passwordEncoder.encode(user.getSenhaHash()));

        ProgressoUsuario progress = new ProgressoUsuario();
        progress.setNivel(1);
        progress.setXp(0);
        progress.setAtualizadoEm(LocalDateTime.now());

        user.setProgressoUsuario(progress);
        progress.setUsuario(user);

        Usuario saved = userRepository.save(user);
        return toResponse(saved);
    }

    /**
     * Atualiza nome e pronome de um usuário existente.
     *
     * @param id      identificador do usuário a ser atualizado
     * @param request DTO contendo os novos dados
     * @return DTO do usuário atualizado
     * @throws ResponseStatusException se o usuário não for encontrado
     */
    public UsuarioResponse update(Integer id, UsuarioRequest request) {
        Usuario user = findEntityById(id);

        user.setNome(request.getNome());
        Pronome pronoun = pronounService.findEntityById(request.getIdPronome());
        user.setPronome(pronoun);

        Usuario updated = userRepository.save(user);
        return toResponse(updated);
    }

    /**
     * Remove um usuário pelo seu identificador.
     *
     * @param id identificador do usuário a ser removido
     * @return DTO do usuário removido
     * @throws ResponseStatusException se o usuário não for encontrado
     */
    public UsuarioResponse delete(Integer id) {
        Usuario user = findEntityById(id);
        userRepository.delete(user);
        return toResponse(user);
    }

    /**
     * Converte DTO de requisição em entidade Usuario.
     *
     * @param request objeto de requisição
     * @return entidade Usuario mapeada
     */
    private Usuario toEntity(UsuarioRequest request) {
        return modelMapper.map(request, Usuario.class);
    }

    /**
     * Converte entidade Usuario em DTO de resposta, incluindo pronome.
     *
     * @param user entidade persistida
     * @return DTO de resposta
     */
    private UsuarioResponse toResponse(Usuario user) {
        UsuarioResponse response = modelMapper.map(user, UsuarioResponse.class);
        response.setPronome(pronounService.toResponse(user.getPronome()));
        return response;
    }

    /**
     * Busca a entidade Usuario pelo ID ou lança ResponseStatusException 404.
     *
     * @param id identificador do usuário
     * @return entidade encontrada
     * @throws ResponseStatusException se não encontrar a entidade
     */
    private Usuario findEntityById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND)
                );
    }
}
