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
 * Controlador REST para operações relacionadas a usuários e seus adjetivos.
 */
@RestController
@RequestMapping("/usuarios")

public class UsuarioController {
    /**
     * Injeção de dependência dos repositórios.
     * Cada repositório é responsável por gerenciar uma entidade específica no banco de dados.
     * Isso permite que possamos fazer consultas, salvamentos e exclusões de forma simples.
     */
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

    /**
     * Construtor que recebe todas as dependências necessárias para o funcionamento do controller.
     * O Spring Boot injeta automaticamente as instâncias dos repositórios e serviços aqui.
     * Ao usar `final` nas variáveis, garantimos que não poderão ser alteradas depois de inicializadas.
     */
    public UsuarioController(ProgressoUsuarioService progressoUsuarioService, UsuarioRepository usuarioRepository, AdjetivoUsuarioRepository adjetivoUsuarioRepository, AvatarUsuarioRepository avatarUsuarioRepository, IngredienteUsuarioRepository ingredienteUsuarioRepository, ReceitaUsuarioRepository receitaUsuarioRepository, ReceitaRepository receitaRepository, TituloUsuarioRepository tituloUsuarioRepository, UsuarioService usuarioService, AdjetivoUsuarioService adjetivoUsuarioService, AvatarUsuarioService avatarUsuarioService, IngredienteUsuarioService ingredienteUsuarioService, TituloUsuarioService tituloUsuarioService, ReceitaUsuarioService receitaUsuarioService) {
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
     * Lista todos os usuários cadastrados
     */
    @GetMapping
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    /**
     * Busca um usuário pelo ID.
     * Caso não encontre, lança um erro 404 (NOT_FOUND).
     */
    @GetMapping("/{idUsuario}")
    public UsuarioResponse buscarUsuario(@PathVariable Integer idUsuario) {
        return usuarioService.buscar(idUsuario);
    }

    /**
     * Atualiza os dados de um usuário existente.
     * <p>
     * - Busca o usuário pelo ID
     * - Atualiza os campos nome, email, pronome
     * - Atualiza a senha apenas se um novo valor for enviado (fazendo o hash)
     */
    @PutMapping("/{idUsuario}")
    public UsuarioResponse alterarUsuario(@PathVariable Integer idUsuario, @RequestBody UsuarioRequest request) {
        return usuarioService.alterar(idUsuario, request);
    }

    /**
     * Remove um usuário pelo ID.
     * Caso não encontre, retorna 404.
     */
    @DeleteMapping("/{idUsuario}")
    public UsuarioResponse removerUsuario(@PathVariable Integer idUsuario) {
        return usuarioService.remover(idUsuario);
    }

    ///* ---------- Adjetivos do Usuário ---------- */

    /**
     * Lista todos os adjetivos associados a um usuário pelo ID.
     */
    @GetMapping("/{idUsuario}/adjetivos")
    public List<AdjetivoUsuarioResponse> listarAdjetivosUsuario(@PathVariable Integer idUsuario) {

        /// Retorna todos os adjetivos do usuário usando o repositório
        return adjetivoUsuarioService.listarTodos(idUsuario);
    }

    /**
     * Busca um adjetivo específico de um usuário.
     */
    @GetMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuarioResponse buscarAdjetivoUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idAdjetivoUsuario) {
        return adjetivoUsuarioService.buscar(idUsuario, idAdjetivoUsuario);
    }

    /**
     * Cria um novo adjetivo associado a um usuário.
     */
    @PostMapping("/{idUsuario}/adjetivos")
    public AdjetivoUsuarioResponse criarAdjetivoUsuario(@PathVariable Integer idUsuario, @RequestBody AdjetivoUsuarioRequest request) {
        return adjetivoUsuarioService.criar(idUsuario, request);
    }

    /**
     * Edita um adjetivo associado a um usuário.
     */
    @PutMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuarioResponse editarAdjetivoUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idAdjetivoUsuario, @RequestBody AdjetivoUsuarioRequest request) {
        return adjetivoUsuarioService.alterar(idUsuario, idAdjetivoUsuario, request);
    }

    /**
     * Remove um adjetivo associado a um usuário.
     */
    @DeleteMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuarioResponse removerAdjetivoUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idAdjetivoUsuario) {
        return adjetivoUsuarioService.remover(idUsuario, idAdjetivoUsuario);
    }

    ///* ---------- Avatares do Usuário ---------- */

    /**
     * Lista todos os avatares associados a um usuário pelo ID.
     */
    @GetMapping("/{idUsuario}/avatares")
    public List<AvatarUsuarioResponse> listarAvataresUsuario(@PathVariable Integer idUsuario) {
        return avatarUsuarioService.listarTodos(idUsuario);
    }

    /**
     * Busca um avatar específico de um usuário.
     */
    @GetMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuarioResponse buscarAvatarUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idAvatarUsuario) {
        return avatarUsuarioService.buscar(idUsuario, idAvatarUsuario);
    }

    /**
     * Cria um novo avatar associado a um usuário.
     */
    @PostMapping("/{idUsuario}/avatares")
    public AvatarUsuarioResponse adicionarReceitaUsuario(@PathVariable Integer idUsuario, @RequestBody AvatarUsuarioRequest request) {
        return avatarUsuarioService.criar(idUsuario, request);
    }

    /**
     * Edita um avatar associado a um usuário.
     */
    @PutMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuarioResponse editarAvatarUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idAvatarUsuario, @RequestBody AvatarUsuarioRequest request) {
       return avatarUsuarioService.alterar(idUsuario, idAvatarUsuario, request);
    }

    /**
     * Remove um avatar associado a um usuário.
     */
    @DeleteMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuarioResponse removerAvatarUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idAvatarUsuario) {
        return avatarUsuarioService.remover(idUsuario, idAvatarUsuario);
    }

    ///* ---------- Estoque Virtual do Usuário ---------- */

    /**
     * Lista todos os ingredientes associados a um usuário (estoque virtual).
     */
    @GetMapping("/{idUsuario}/estoque-virtual")
    public List<IngredienteUsuarioResponse> listarIngredienteUsuario(@PathVariable Integer idUsuario) {
        return ingredienteUsuarioService.listarTodos(idUsuario);
    }

    /**
     * Busca um ingrediente específico do estoque virtual de um usuário.
     */
    @GetMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuarioResponse buscarIngredienteUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idIngredienteUsuario) {
        return ingredienteUsuarioService.buscar(idUsuario, idIngredienteUsuario);
    }

    /**
     * Cria um novo ingrediente no estoque virtual associado a um usuário.
     */
    @PostMapping("/{idUsuario}/estoque-virtual")
    public IngredienteUsuarioResponse criarIngredienteUsuario(@PathVariable Integer idUsuario, @RequestBody IngredienteUsuarioRequest request) {
        return ingredienteUsuarioService.criar(idUsuario, request);
    }

    /**
     * Edita um ingrediente do estoque virtual associado a um usuário.
     */
    @PutMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuarioResponse editarIngredienteUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idIngredienteUsuario, @RequestBody IngredienteUsuarioRequest request) {
        return ingredienteUsuarioService.alterar(idUsuario, idIngredienteUsuario, request);
    }

    /**
     * Remove um ingrediente do estoque virtual associado a um usuário.
     */
    @DeleteMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuarioResponse removerIngredienteUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idIngredienteUsuario) {
        return ingredienteUsuarioService.remover(idUsuario, idIngredienteUsuario);
    }


    ///* ---------- Progresso do Usuário ---------- */
    /**
     * Busca o progresso do usuário pelo ID.
     */
    @GetMapping("/{idUsuario}/progresso")
    public ProgressoUsuarioResponse buscarProgressoUsuario(@PathVariable Integer idUsuario) {
        return progressoUsuarioService.buscar(idUsuario);
    }

    /**
     * Edita o progresso do usuário.
     */
    @PutMapping("/{idUsuario}/progresso")
    public ProgressoUsuarioResponse editarProgressoUsuario(@PathVariable Integer idUsuario, @RequestBody ProgressoUsuarioRequest request) {
        return progressoUsuarioService.alterar(idUsuario, request);
    }

    ///* ---------- Receitas Concluídas pelo Usuário ---------- */
    /**
     * Lista todas as receitas concluídas de um usuário.
     */
    @GetMapping("/{idUsuario}/receitas")
    public List<ReceitaUsuarioResponse> listarReceitaUsuario(@PathVariable Integer idUsuario) {
        return receitaUsuarioService.listarTodos(idUsuario);
    }

    /**
     * Busca uma receita concluída específica de um usuário pelo ID da receita.
     */
    @GetMapping("/{idUsuario}/receitas/{idReceitaUsuario}")
    public ReceitaUsuarioResponse buscarReceitaUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idReceitaUsuario) {
        return receitaUsuarioService.buscar(idUsuario, idReceitaUsuario);
    }

    /**
     * Adiciona uma nova receita concluída pelo usuário.
     */
    @PostMapping("/{idUsuario}/receitas")
    public ReceitaUsuarioResponse adicionarReceitaUsuario(@PathVariable Integer idUsuario, @RequestBody ReceitaUsuarioRequest receitaUsuario) {
        return receitaUsuarioService.criar(idUsuario, receitaUsuario);
    }

    /**
     * Remove uma receita concluída associada a um usuário.
     */
    @DeleteMapping("/{idUsuario}/receitas/{idReceitaUsuario}")
    public ReceitaUsuarioResponse removerReceitaUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idReceitaUsuario) {
        return receitaUsuarioService.remover(idUsuario, idReceitaUsuario);
    }

    ///* ---------- Títulos do Usuário ---------- */

    /**
     * Lista todos os títulos associados a um usuário pelo ID.
     */
    @GetMapping("/{idUsuario}/titulos")
    public List<TituloUsuarioResponse> listarTitulosUsuario(@PathVariable Integer idUsuario) {
    return tituloUsuarioService.listarTodos(idUsuario);
    }

    /**
     * Busca um título específico de um usuário.
     */
    @GetMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuarioResponse buscarTituloUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idTituloUsuario) {
    return tituloUsuarioService.buscar(idUsuario, idTituloUsuario);
    }

    /**
     * Cria um novo título associado a um usuário.
     */
    @PostMapping("/{idUsuario}/titulos")
    public TituloUsuarioResponse criarTituloUsuario(@PathVariable Integer idUsuario, @RequestBody TituloUsuarioRequest request) {
        return tituloUsuarioService.criar(idUsuario, request);
    }

    /**
     * Edita um título associado a um usuário.
     */
    @PutMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuarioResponse editarTituloUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idTituloUsuario, @RequestBody TituloUsuarioRequest request) {
       return tituloUsuarioService.alterar(idUsuario, idTituloUsuario, request);
    }

    /**
     * Remove um título associado a um usuário.
     */
    @DeleteMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuarioResponse removerTituloUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idTituloUsuario) {
        return tituloUsuarioService.remover(idUsuario, idTituloUsuario);
    }
}
