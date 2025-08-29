package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Usuario.UsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.entities.ProgressoUsuario;
import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private static final String ERROR_RAW_PASSWORD = "Senha não pode ser nula";
    private static final String ERROR_USER_NOT_FOUND_EMAIL = "Usuário não encontrado com o email: ";

    private final UsuarioRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final PronomeService pronounService;
    private final TituloUsuarioService userTitleService;
    private final AdjetivoUsuarioService userAdjectiveService;
    private final ReceitaUsuarioService userRecipeService;
    private final ProgressoUsuarioService userProgressService;
    private final IngredienteUsuarioService userIngredientService;
    private final AvatarUsuarioService userAvatarService;

    public UsuarioService(UsuarioRepository userRepository,
                          ModelMapper modelMapper,
                          PasswordEncoder passwordEncoder,
                          PronomeService pronounService, TituloUsuarioService userTitleService, AdjetivoUsuarioService userAdjectiveService, ReceitaUsuarioService userRecipeService, ProgressoUsuarioService userProgressService, IngredienteUsuarioService userIngredientService, AvatarUsuarioService userAvatarService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.pronounService = pronounService;
        this.userTitleService = userTitleService;
        this.userAdjectiveService = userAdjectiveService;
        this.userRecipeService = userRecipeService;
        this.userProgressService = userProgressService;
        this.userIngredientService = userIngredientService;
        this.userAvatarService = userAvatarService;
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
        Usuario user = new Usuario();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());

        if (request.getSenha() == null) {
            throw new IllegalArgumentException(ERROR_RAW_PASSWORD);
        }
        String password = passwordEncoder.encode(request.getSenha());

        user.setSenha(password);
        user.setDataCadastro(LocalDateTime.now());

        ProgressoUsuario progress = new ProgressoUsuario();
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

        if (request.getNome() != null && !request.getNome().isBlank()) {
            user.setNome(request.getNome());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        if (request.getIdPronome() != null) {
            Pronome pronoun = pronounService.findEntityById(request.getIdPronome());
            user.setPronome(pronoun);
        }

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
     * Converte entidade Usuario em DTO de resposta, incluindo pronome se existir.
     *
     * @param user entidade persistida
     * @return DTO de resposta
     */
    private UsuarioResponse toResponse(Usuario user) {
        UsuarioResponse response = modelMapper.map(user, UsuarioResponse.class);

        if (user.getPronome() != null) {
            response.setPronome(pronounService.toResponse(user.getPronome()));
        }

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

    public Usuario findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND_EMAIL + email));
    }

    public String getLoggedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public UsuarioResponse getUsuarioResponseById(Integer id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_USER_NOT_FOUND));

        UsuarioResponse response = new UsuarioResponse();
        response.setNome(user.getNome());
        response.setEmail(user.getEmail());
        response.setPronome(pronounService.toResponse(user.getPronome()));
        response.setTitulos((userTitleService.toResponseList(user.getTitulos())));
        response.setAdjetivos(userAdjectiveService.toResponseList(user.getAdjetivos()));
        response.setReceitasConcluidas(userRecipeService.toResponseList(user.getReceitasConcluidas()));
        response.setProgresso(userProgressService.toResponse(user.getProgressoUsuario()));
        response.setIngredientes(userIngredientService.toResponseList(user.getIngredientes()));
        response.setAvatares(userAvatarService.toResponseList(user.getAvatares()));
        return response;
    }
}