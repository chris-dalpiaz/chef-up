package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.*;
import com.entra21.chef_up.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para operações relacionadas a usuários e seus adjetivos.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    // Repositórios para acesso ao banco de dados
    private final UsuarioRepository usuarioRepository;
    private final AdjetivoRepository adjetivoRepository;
    private final AdjetivoUsuarioRepository adjetivoUsuarioRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarUsuarioRepository avatarUsuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final IngredienteUsuarioRepository ingredienteUsuarioRepository;
    private final ProgressoUsuarioRepository progressoUsuarioRepository;

    // Construtor com injeção de dependência
    public UsuarioController(UsuarioRepository usuarioRepository,
                             AdjetivoRepository adjetivoRepository,
                             AdjetivoUsuarioRepository adjetivoUsuarioRepository,
                             AvatarRepository avatarRepository,
                             AvatarUsuarioRepository avatarUsuarioRepository, PasswordEncoder passwordEncoder, IngredienteUsuarioRepository ingredienteUsuarioRepository, ProgressoUsuarioRepository progressoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.adjetivoRepository = adjetivoRepository;
        this.avatarRepository = avatarRepository;
        this.adjetivoUsuarioRepository = adjetivoUsuarioRepository;
        this.avatarUsuarioRepository = avatarUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.ingredienteUsuarioRepository = ingredienteUsuarioRepository;
        this.progressoUsuarioRepository = progressoUsuarioRepository;
    }

    // Lista todos os usuários cadastrados
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // Busca um usuário pelo ID
    @GetMapping("/{idUsuario}")
    public Usuario buscarUsuario(@PathVariable Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    // Atualiza os dados de um usuário existente
    @PutMapping("/{idUsuario}")
    public Usuario alterarUsuario(@PathVariable Integer idUsuario,
                                  @RequestBody Usuario usuario) {
        Usuario alterar = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        alterar.setNome(usuario.getNome());
        alterar.setEmail(usuario.getEmail());

        if (usuario.getSenhaHash() != null && !usuario.getSenhaHash().isEmpty()) {
            alterar.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash())); // re-hash
        }

        alterar.setSenhaHash(usuario.getSenhaHash());
        alterar.setPronome(usuario.getPronome());

        return usuarioRepository.save(alterar);
    }

    // Remove um usuário pelo ID
    @DeleteMapping("/{idUsuario}")
    public Usuario removerUsuario(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuarioRepository.delete(usuario);
        return usuario;
    }

    // Adjetivos do Usuário

    // Lista todos os adjetivos associados a um usuário
    @GetMapping("/{idUsuario}/adjetivos")
    public List<AdjetivoUsuario> listarAdjetivos(@PathVariable Integer idUsuario) {
        return adjetivoUsuarioRepository.findByUsuarioId(idUsuario);
    }

    // Busca um adjetivo específico de um usuário
    @GetMapping("/{idUsuario}/adjetivos/{idAdjetivo}")
    public AdjetivoUsuario buscarAdjetivo(@PathVariable Integer idUsuario,
                                          @PathVariable Integer idAdjetivo) {
        AdjetivoUsuario adjetivo = adjetivoUsuarioRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        if (!adjetivo.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        return adjetivo;
    }

    // Cria um novo adjetivo associado a um usuário
    @PostMapping("/{idUsuario}/adjetivos")
    public AdjetivoUsuario criarAdjetivoUsuario(@PathVariable Integer idUsuario,
                                                @RequestBody AdjetivoUsuario adjetivoUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        adjetivoUsuario.setUsuario(usuario);
        return adjetivoUsuarioRepository.save(adjetivoUsuario);
    }

    // Edita um adjetivo associado a um usuário
    @PutMapping("/{idUsuario}/adjetivos/{idAdjetivo}")
    public AdjetivoUsuario editarAdjetivoUsuario(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idAdjetivo,
                                                 @RequestBody AdjetivoUsuario adjetivoUsuario) {
        AdjetivoUsuario alterar = adjetivoUsuarioRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        if (!alterar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        alterar.setAdjetivo(adjetivoUsuario.getAdjetivo());
        return adjetivoUsuarioRepository.save(alterar);
    }

    // Remove um adjetivo associado a um usuário
    @DeleteMapping("/{idUsuario}/adjetivos/{idAdjetivo}")
    public AdjetivoUsuario removerAdjetivoUsuario(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idAdjetivo) {
        AdjetivoUsuario adjetivo = adjetivoUsuarioRepository.findById(idAdjetivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        if (!adjetivo.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        adjetivoUsuarioRepository.delete(adjetivo);
        return adjetivo;
    }

    // Avatares do Usuário

    // Lista todos os avatares associados a um usuário
    @GetMapping("/{idUsuario}/avatares")
    public List<AvatarUsuario> listarAvatares(@PathVariable Integer idUsuario) {
        return avatarUsuarioRepository.findByUsuarioId(idUsuario);
    }

    // Busca um avatar específico de um usuário
    @GetMapping("/{idUsuario}/avatares/{idAvatar}")
    public AvatarUsuario buscarAvatar(@PathVariable Integer idUsuario,
                                      @PathVariable Integer idAvatar) {
        AvatarUsuario avatar = avatarUsuarioRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        if (!avatar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        return avatar;
    }

    // Cria um novo avatar associado a um usuário
    @PostMapping("/{idUsuario}/avatares")
    public AvatarUsuario criarAvatarUsuario(@PathVariable Integer idUsuario,
                                            @RequestBody AvatarUsuario avatarUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        avatarUsuario.setDesbloqueadoEm(LocalDateTime.now());

        avatarUsuario.setUsuario(usuario);

        return avatarUsuarioRepository.save(avatarUsuario);
    }

    // Edita um avatar associado a um usuário
    @PutMapping("/{idUsuario}/avatares/{idAvatar}")
    public AvatarUsuario editarAvatarUsuario(@PathVariable Integer idUsuario,
                                             @PathVariable Integer idAvatar,
                                             @RequestBody AvatarUsuario avatarUsuario) {

        AvatarUsuario alterar = avatarUsuarioRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        if (!alterar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        alterar.setAvatar(avatarUsuario.getAvatar());
        return avatarUsuarioRepository.save(alterar);
    }

    // Remove um avatar associado a um usuário
    @DeleteMapping("/{idUsuario}/avatares/{idAvatar}")
    public AvatarUsuario removerAvatarUsuario(@PathVariable Integer idUsuario,
                                              @PathVariable Integer idAvatar) {
        AvatarUsuario avatar = avatarUsuarioRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        if (!avatar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        avatarUsuarioRepository.delete(avatar);
        return avatar;
    }

    // Ingredientes do Usuário (estoque virtual)

    // Lista todos os ingredientes associados a um usuário
    @GetMapping("/{idUsuario}/estoquevirtual")
    public List<IngredienteUsuario> listarIngredienteUsuario(@PathVariable Integer idUsuario) {
        return ingredienteUsuarioRepository.findByUsuarioId(idUsuario);
    }

    // Busca um ingrediente específico de um usuário
    @GetMapping("/{idUsuario}/estoquevirtual/{idIngrediente}")
    public IngredienteUsuario buscarIngredienteUsuario(@PathVariable Integer idUsuario,
                                                       @PathVariable Integer idIngrediente) {
        IngredienteUsuario estoqueVirtual = ingredienteUsuarioRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        if (!estoqueVirtual.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        return estoqueVirtual;
    }

    // Cria um novo ingrediente associado a um usuário
    @PostMapping("/{idUsuario}/estoquevirtual")
    public IngredienteUsuario criarIngredienteUsuario(@PathVariable Integer idUsuario,
                                                      @RequestBody IngredienteUsuario ingredienteUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        ingredienteUsuario.setDataAdicionada(LocalDateTime.now());
        ingredienteUsuario.setUsuario(usuario);

        return ingredienteUsuarioRepository.save(ingredienteUsuario);
    }

    // Edita um ingrediente associado a um usuário
    @PutMapping("/{idUsuario}/estoquevirtual/{idIngrediente}")
    public IngredienteUsuario editarIngredienteUsuario(@PathVariable Integer idUsuario,
                                                       @PathVariable Integer idIngrediente,
                                                       @RequestBody IngredienteUsuario ingredienteUsuario) {

        IngredienteUsuario alterar = ingredienteUsuarioRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        if (!alterar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        alterar.setIngrediente(ingredienteUsuario.getIngrediente());
        return ingredienteUsuarioRepository.save(alterar);
    }

    // Remove um ingrediente associado a um usuário
    @DeleteMapping("/{idUsuario}/estoquevirtual/{idIngrediente}")
    public IngredienteUsuario removerIngredienteUsuario(@PathVariable Integer idUsuario,
                                                        @PathVariable Integer idIngrediente) {
        IngredienteUsuario ingrediente = ingredienteUsuarioRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        if (!ingrediente.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        ingredienteUsuarioRepository.delete(ingrediente);
        return ingrediente;
    }

    // Progresso do Usuario

    // Busca o progresso de um usuário
    @GetMapping("/{idUsuario}/progresso")
    public ProgressoUsuario buscarProgressoUsuario(@PathVariable Integer idUsuario) {

        return progressoUsuarioRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progresso não encontrado"));
    }

    // Edita o progresso associado a um usuário
    @PutMapping("/{idUsuario}/progresso")
    public ProgressoUsuario editarProgressoUsuario(@PathVariable Integer idUsuario,
                                                   @RequestBody ProgressoUsuario progressoUsuario) {

        ProgressoUsuario alterar = progressoUsuarioRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progresso do usuário não encontrado"));

        if (!alterar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Progresso não pertence ao usuário");
        }

        alterar.setXp(progressoUsuario.getXp());
        alterar.setNivel(progressoUsuario.getNivel());
        alterar.setAtualizadoEm(LocalDateTime.now());

        progressoUsuarioRepository.save(alterar);
        return progressoUsuario;
    }
}
