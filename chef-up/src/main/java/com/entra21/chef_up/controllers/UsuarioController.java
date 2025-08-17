package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioRequest;
import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioResponse;
import com.entra21.chef_up.dtos.AvatarUsuario.AvatarUsuarioRequest;
import com.entra21.chef_up.dtos.AvatarUsuario.AvatarUsuarioResponse;
import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioRequest;
import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioResponse;
import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioRequest;
import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioResponse;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioRequest;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioResponse;
import com.entra21.chef_up.dtos.TituloUsuario.TituloUsuarioRequest;
import com.entra21.chef_up.dtos.TituloUsuario.TituloUsuarioResponse;
import com.entra21.chef_up.dtos.Usuario.UsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.repositories.*;
import com.entra21.chef_up.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Injeção de dependência dos repositórios.
 * Cada repositório é responsável por gerenciar uma entidade específica no banco de dados.
 * Isso permite que possamos fazer consultas, salvamentos e exclusões de forma simples.
 */
@RestController
@RequestMapping("/usuarios")

public class UsuarioController {

    private final ProgressoUsuarioService progressoUsuarioService;
    private final UsuarioRepository usuarioRepository;
    private final AdjetivoUsuarioRepository adjetivoUsuarioRepository;
    private final AvatarUsuarioRepository avatarUsuarioRepository;
    private final IngredienteUsuarioRepository ingredienteUsuarioRepository;
    private final ReceitaUsuarioRepository receitaUsuarioRepository;
    private final ReceitaRepository receitaRepository;
    private final TituloUsuarioRepository tituloUsuarioRepository;
    private final UsuarioService usuarioService;
    private final AdjetivoUsuarioService adjetivoUsuarioService;
    private final AvatarUsuarioService avatarUsuarioService;
    private final IngredienteUsuarioService ingredienteUsuarioService;
    private final TituloUsuarioService tituloUsuarioService;
    private final ReceitaUsuarioService receitaUsuarioService;

    public UsuarioController(ProgressoUsuarioService progressoUsuarioService,
                             UsuarioRepository usuarioRepository,
                             AdjetivoUsuarioRepository adjetivoUsuarioRepository,
                             AvatarUsuarioRepository avatarUsuarioRepository,
                             IngredienteUsuarioRepository ingredienteUsuarioRepository,
                             ReceitaUsuarioRepository receitaUsuarioRepository,
                             ReceitaRepository receitaRepository,
                             TituloUsuarioRepository tituloUsuarioRepository,
                             UsuarioService usuarioService,
                             AdjetivoUsuarioService adjetivoUsuarioService,
                             AvatarUsuarioService avatarUsuarioService,
                             IngredienteUsuarioService ingredienteUsuarioService,
                             TituloUsuarioService tituloUsuarioService,
                             ReceitaUsuarioService receitaUsuarioService) {
        this.progressoUsuarioService = progressoUsuarioService;
        this.usuarioRepository = usuarioRepository;
        this.adjetivoUsuarioRepository = adjetivoUsuarioRepository;
        this.avatarUsuarioRepository = avatarUsuarioRepository;
        this.ingredienteUsuarioRepository = ingredienteUsuarioRepository;
        this.receitaUsuarioRepository = receitaUsuarioRepository;
        this.receitaRepository = receitaRepository;
        this.tituloUsuarioRepository = tituloUsuarioRepository;
        this.usuarioService = usuarioService;
        this.adjetivoUsuarioService = adjetivoUsuarioService;
        this.avatarUsuarioService = avatarUsuarioService;
        this.ingredienteUsuarioService = ingredienteUsuarioService;
        this.tituloUsuarioService = tituloUsuarioService;
        this.receitaUsuarioService = receitaUsuarioService;
    }

    /**
     * Lista todos os usuários cadastrados.
     */
    @GetMapping
    public List<UsuarioResponse> listUsers() {
        return usuarioService.listAll();
    }

    /**
     * Busca um usuário pelo ID.
     */
    @GetMapping("/{idUsuario}")
    public UsuarioResponse getUser(@PathVariable Integer idUsuario) {
        return usuarioService.getById(idUsuario);
    }

    /**
     * Atualiza os dados de um usuário existente.
     */
    @PutMapping("/{idUsuario}")
    public UsuarioResponse updateUser(@PathVariable Integer idUsuario,
                                      @RequestBody UsuarioRequest request) {
        return usuarioService.update(idUsuario, request);
    }

    /**
     * Remove um usuário pelo ID.
     */
    @DeleteMapping("/{idUsuario}")
    public UsuarioResponse deleteUser(@PathVariable Integer idUsuario) {
        return usuarioService.delete(idUsuario);
    }

    /* ---------- Adjetivos do Usuário ---------- */

    @GetMapping("/{idUsuario}/adjetivos")
    public List<AdjetivoUsuarioResponse> listUserAdjectives(@PathVariable Integer idUsuario) {
        return adjetivoUsuarioService.listByUser(idUsuario);
    }

    @GetMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuarioResponse getUserAdjective(@PathVariable Integer idUsuario,
                                                    @PathVariable Integer idAdjetivoUsuario) {
        return adjetivoUsuarioService.getById(idUsuario, idAdjetivoUsuario);
    }

    @PostMapping("/{idUsuario}/adjetivos")
    public AdjetivoUsuarioResponse createUserAdjective(@PathVariable Integer idUsuario,
                                                       @RequestBody AdjetivoUsuarioRequest request) {
        return adjetivoUsuarioService.create(idUsuario, request);
    }

    @PutMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuarioResponse updateUserAdjective(@PathVariable Integer idUsuario,
                                                       @PathVariable Integer idAdjetivoUsuario,
                                                       @RequestBody AdjetivoUsuarioRequest request) {
        return adjetivoUsuarioService.update(idUsuario, idAdjetivoUsuario, request);
    }

    @DeleteMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuarioResponse deleteUserAdjective(@PathVariable Integer idUsuario,
                                                       @PathVariable Integer idAdjetivoUsuario) {
        return adjetivoUsuarioService.delete(idUsuario, idAdjetivoUsuario);
    }

    /* ---------- Avatares do Usuário ---------- */

    @GetMapping("/{idUsuario}/avatares")
    public List<AvatarUsuarioResponse> listUserAvatars(@PathVariable Integer idUsuario) {
        return avatarUsuarioService.listByUser(idUsuario);
    }

    @GetMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuarioResponse getUserAvatar(@PathVariable Integer idUsuario,
                                               @PathVariable Integer idAvatarUsuario) {
        return avatarUsuarioService.getById(idUsuario, idAvatarUsuario);
    }

    @PostMapping("/{idUsuario}/avatares")
    public AvatarUsuarioResponse createUserAvatar(@PathVariable Integer idUsuario,
                                                  @RequestBody AvatarUsuarioRequest request) {
        return avatarUsuarioService.create(idUsuario, request);
    }

    @PutMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuarioResponse updateUserAvatar(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idAvatarUsuario,
                                                  @RequestBody AvatarUsuarioRequest request) {
        return avatarUsuarioService.update(idUsuario, idAvatarUsuario, request);
    }

    @DeleteMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuarioResponse deleteUserAvatar(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idAvatarUsuario) {
        return avatarUsuarioService.delete(idUsuario, idAvatarUsuario);
    }

    /* ---------- Estoque Virtual do Usuário ---------- */

    @GetMapping("/{idUsuario}/estoque-virtual")
    public List<IngredienteUsuarioResponse> listUserIngredients(@PathVariable Integer idUsuario) {
        return ingredienteUsuarioService.listByUser(idUsuario);
    }

    @GetMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuarioResponse getUserIngredient(@PathVariable Integer idUsuario,
                                                        @PathVariable Integer idIngredienteUsuario) {
        return ingredienteUsuarioService.getById(idUsuario, idIngredienteUsuario);
    }

    @PostMapping("/{idUsuario}/estoque-virtual")
    public IngredienteUsuarioResponse createUserIngredient(@PathVariable Integer idUsuario,
                                                           @RequestBody IngredienteUsuarioRequest request) {
        return ingredienteUsuarioService.create(idUsuario, request);
    }

    @PutMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuarioResponse updateUserIngredient(@PathVariable Integer idUsuario,
                                                           @PathVariable Integer idIngredienteUsuario,
                                                           @RequestBody IngredienteUsuarioRequest request) {
        return ingredienteUsuarioService.update(idUsuario, idIngredienteUsuario, request);
    }

    @DeleteMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuarioResponse deleteUserIngredient(@PathVariable Integer idUsuario,
                                                           @PathVariable Integer idIngredienteUsuario) {
        return ingredienteUsuarioService.delete(idUsuario, idIngredienteUsuario);
    }

    /* ---------- Progresso do Usuário ---------- */

    @GetMapping("/{idUsuario}/progresso")
    public ProgressoUsuarioResponse getUserProgress(@PathVariable Integer idUsuario) {
        return progressoUsuarioService.getByUserId(idUsuario);
    }

    @PutMapping("/{idUsuario}/progresso")
    public ProgressoUsuarioResponse updateUserProgress(@PathVariable Integer idUsuario,
                                                       @RequestBody ProgressoUsuarioRequest request) {
        return progressoUsuarioService.update(idUsuario, request);
    }

    /* ---------- Receitas Concluídas pelo Usuário ---------- */

    @GetMapping("/{idUsuario}/receitas")
    public List<ReceitaUsuarioResponse> listUserRecipes(@PathVariable Integer idUsuario) {
        return receitaUsuarioService.listByUser(idUsuario);
    }

    @GetMapping("/{idUsuario}/receitas/{idReceitaUsuario}")
    public ReceitaUsuarioResponse getUserRecipe(@PathVariable Integer idUsuario,
                                                @PathVariable Integer idReceitaUsuario) {
        return receitaUsuarioService.getById(idUsuario, idReceitaUsuario);
    }

    @PutMapping("/{idUsuario}/receitas/{idReceitaUsuario}")
    public ReceitaUsuarioResponse updateUserRecipe(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idReceitaUsuario,
                                                 @RequestBody ReceitaUsuarioRequest request) {
        return receitaUsuarioService.update(idUsuario, idReceitaUsuario, request);
    }

    @PostMapping("/{idUsuario}/receitas")
    public ReceitaUsuarioResponse createUserRecipe(@PathVariable Integer idUsuario,
                                                   @RequestBody ReceitaUsuarioRequest request) {
        return receitaUsuarioService.create(idUsuario, request);
    }

    @DeleteMapping("/{idUsuario}/receitas/{idReceitaUsuario}")
    public ReceitaUsuarioResponse deleteUserRecipe(@PathVariable Integer idUsuario,
                                                   @PathVariable Integer idReceitaUsuario) {
        return receitaUsuarioService.delete(idUsuario, idReceitaUsuario);
    }

    /* ---------- Títulos do Usuário ---------- */

    @GetMapping("/{idUsuario}/titulos")
    public List<TituloUsuarioResponse> listUserTitles(@PathVariable Integer idUsuario) {
        return tituloUsuarioService.listAll(idUsuario);
    }

    @GetMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuarioResponse getUserTitle(@PathVariable Integer idUsuario,
                                              @PathVariable Integer idTituloUsuario) {
        return tituloUsuarioService.getById(idUsuario, idTituloUsuario);
    }

    @PostMapping("/{idUsuario}/titulos")
    public TituloUsuarioResponse createUserTitle(@PathVariable Integer idUsuario,
                                                 @RequestBody TituloUsuarioRequest request) {
        return tituloUsuarioService.create(idUsuario, request);
    }

    @PutMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuarioResponse updateUserTitle(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idTituloUsuario,
                                                 @RequestBody TituloUsuarioRequest request) {
        return tituloUsuarioService.update(idUsuario, idTituloUsuario, request);
    }

    @DeleteMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuarioResponse deleteUserTitle(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idTituloUsuario) {
        return tituloUsuarioService.delete(idUsuario, idTituloUsuario);
    }

}