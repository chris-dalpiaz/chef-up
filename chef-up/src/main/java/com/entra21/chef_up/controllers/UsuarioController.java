package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.AdjetivoUsuario;
import com.entra21.chef_up.entities.AvatarUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repository.*;
import org.springframework.http.HttpStatus;
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

    // Construtor com injeção de dependência
    public UsuarioController(UsuarioRepository usuarioRepository,
                             AdjetivoRepository adjetivoRepository,
                             AdjetivoUsuarioRepository adjetivoUsuarioRepository,
                             AvatarRepository avatarRepository,
                             AvatarUsuarioRepository avatarUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.adjetivoRepository = adjetivoRepository;
        this.avatarRepository = avatarRepository;
        this.adjetivoUsuarioRepository = adjetivoUsuarioRepository;
        this.avatarUsuarioRepository = avatarUsuarioRepository;
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

    // Cria um novo usuário
    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        usuario.setDataCadastro(LocalDateTime.now());
        //usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));
        return usuarioRepository.save(usuario);
    }

    // Atualiza os dados de um usuário existente
    @PutMapping("/{idUsuario}")
    public Usuario alterarUsuario(@PathVariable Integer idUsuario,
                                  @RequestBody Usuario usuario) {
        Usuario alterar = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        alterar.setNome(usuario.getNome());
        alterar.setEmail(usuario.getEmail());
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
}
