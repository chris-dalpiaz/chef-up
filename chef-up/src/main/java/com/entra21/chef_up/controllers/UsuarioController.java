package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Adjetivo.AdjetivoResponse;
import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioRequest;
import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioResponse;
import com.entra21.chef_up.dtos.Usuario.UsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.entities.*;
import com.entra21.chef_up.repositories.*;
import com.entra21.chef_up.services.ProgressoUsuarioService;
import com.entra21.chef_up.services.UsuarioService;
import org.modelmapper.ModelMapper;
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
    /**
     * Injeção de dependência dos repositórios.
     * Cada repositório é responsável por gerenciar uma entidade específica no banco de dados.
     * Isso permite que possamos fazer consultas, salvamentos e exclusões de forma simples.
     */
    private final ProgressoUsuarioService progressoUsuarioService;
    private final UsuarioRepository usuarioRepository;
    private final AdjetivoRepository adjetivoRepository;
    private final AdjetivoUsuarioRepository adjetivoUsuarioRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarUsuarioRepository avatarUsuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final IngredienteUsuarioRepository ingredienteUsuarioRepository;
    private final ProgressoUsuarioRepository progressoUsuarioRepository;
    private final ReceitaUsuarioRepository receitaUsuarioRepository;
    private final ReceitaRepository receitaRepository;
    private final TituloUsuarioRepository tituloUsuarioRepository;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;
    /**
     * Construtor que recebe todas as dependências necessárias para o funcionamento do controller.
     * O Spring Boot injeta automaticamente as instâncias dos repositórios e serviços aqui.
     * Ao usar `final` nas variáveis, garantimos que não poderão ser alteradas depois de inicializadas.
     */
    public UsuarioController(
            ProgressoUsuarioService progressoUsuarioService, UsuarioRepository usuarioRepository,
            AdjetivoRepository adjetivoRepository,
            AdjetivoUsuarioRepository adjetivoUsuarioRepository,
            AvatarRepository avatarRepository,
            AvatarUsuarioRepository avatarUsuarioRepository,
            PasswordEncoder passwordEncoder,
            IngredienteUsuarioRepository ingredienteUsuarioRepository,
            ProgressoUsuarioRepository progressoUsuarioRepository,
            ReceitaUsuarioRepository receitaUsuarioRepository,
            ReceitaRepository receitaRepository,
            TituloUsuarioRepository tituloUsuarioRepository,
            UsuarioService usuarioService,
            ModelMapper modelMapper
    ) {
        this.progressoUsuarioService = progressoUsuarioService;
        /// Associação dos parâmetros recebidos com os atributos da classe.
        /// Isso permite que os métodos do controller acessem os repositórios e serviços
        this.usuarioRepository = usuarioRepository;
        this.adjetivoRepository = adjetivoRepository;
        this.adjetivoUsuarioRepository = adjetivoUsuarioRepository;
        this.avatarRepository = avatarRepository;
        this.avatarUsuarioRepository = avatarUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.ingredienteUsuarioRepository = ingredienteUsuarioRepository;
        this.progressoUsuarioRepository = progressoUsuarioRepository;
        this.receitaUsuarioRepository = receitaUsuarioRepository;
        this.receitaRepository = receitaRepository;
        this.tituloUsuarioRepository = tituloUsuarioRepository;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
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
    public UsuarioResponse alterarUsuario(@PathVariable Integer idUsuario,
                                  @RequestBody UsuarioRequest request) {
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
    public List<AdjetivoUsuario> listarAdjetivosUsuario(@PathVariable Integer idUsuario) {

        /// Retorna todos os adjetivos do usuário usando o repositório
        return adjetivoUsuarioRepository.findByUsuarioId(idUsuario);
    }

    /**
     * Busca um adjetivo específico de um usuário.
     */
    @GetMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuario buscarAdjetivoUsuario(@PathVariable Integer idUsuario,
                                          @PathVariable Integer idAdjetivoUsuario) {

        /// Busca o adjetivo pelo ID ou lança erro 404 se não existir
        AdjetivoUsuario adjetivo = adjetivoUsuarioRepository.findById(idAdjetivoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        /// Verifica se o adjetivo pertence ao usuário; se não, lança erro 400
        if (!adjetivo.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        /// Retorna o adjetivo válido
        return adjetivo;
    }

    /**
     * Cria um novo adjetivo associado a um usuário.
     */
    @PostMapping("/{idUsuario}/adjetivos")
    public AdjetivoUsuario criarAdjetivoUsuario(@PathVariable Integer idUsuario,
                                                @RequestBody AdjetivoUsuario adjetivoUsuario) {

        /// Busca o usuário pelo ID ou lança erro 404 se não existir
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        /// Associa o usuário ao novo adjetivo
        adjetivoUsuario.setUsuario(usuario);

        /// Salva e retorna o adjetivo criado no banco
        return adjetivoUsuarioRepository.save(adjetivoUsuario);
    }

    /**
     * Edita um adjetivo associado a um usuário.
     */
    @PutMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuario editarAdjetivoUsuario(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idAdjetivoUsuario,
                                                 @RequestBody AdjetivoUsuario adjetivoUsuario) {
        /// Busca o adjetivo a ser alterado ou lança erro 404
        AdjetivoUsuario alterar = adjetivoUsuarioRepository.findById(idAdjetivoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        /// Verifica se o adjetivo pertence ao usuário; se não, lança erro 400
        if (!alterar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        /// Atualiza o campo adjetivo com os dados recebidos
        alterar.setAdjetivo(adjetivoUsuario.getAdjetivo());

        /// Salva e retorna o adjetivo atualizado
        return adjetivoUsuarioRepository.save(alterar);
    }

    /**
     * Remove um adjetivo associado a um usuário.
     */
    @DeleteMapping("/{idUsuario}/adjetivos/{idAdjetivoUsuario}")
    public AdjetivoUsuario removerAdjetivoUsuario(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idAdjetivoUsuario) {
        /// Busca o adjetivo pelo ID ou lança erro 404
        AdjetivoUsuario adjetivo = adjetivoUsuarioRepository.findById(idAdjetivoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        /// Verifica se o adjetivo pertence ao usuário; se não, lança erro 400
        if (!adjetivo.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        /// Deleta o adjetivo do banco
        adjetivoUsuarioRepository.delete(adjetivo);

        /// Retorna o adjetivo removido (opcional)
        return adjetivo;
    }

    ///* ---------- Avatares do Usuário ---------- */

    /**
     * Lista todos os avatares associados a um usuário pelo ID.
     */
    @GetMapping("/{idUsuario}/avatares")
    public List<AvatarUsuario> listarAvataresUsuario(@PathVariable Integer idUsuario) {
        // Retorna a lista de avatares do usuário pelo ID
        return avatarUsuarioRepository.findByUsuarioId(idUsuario);
    }

    /**
     * Busca um avatar específico de um usuário.
     */
    @GetMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuario buscarAvatarUsuario(@PathVariable Integer idUsuario,
                                      @PathVariable Integer idAvatarUsuario) {
        /// Busca o avatar pelo ID ou lança erro 404 se não encontrado
        AvatarUsuario avatar = avatarUsuarioRepository.findById(idAvatarUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        /// Verifica se o avatar pertence ao usuário; se não, lança erro 400
        if (!avatar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        /// Retorna o avatar válido
        return avatar;
    }

    /**
     * Cria um novo avatar associado a um usuário.
     */
    @PostMapping("/{idUsuario}/avatares")
    public AvatarUsuario adicionarReceitaUsuario(@PathVariable Integer idUsuario,
                                                 @RequestBody AvatarUsuario avatarUsuario) {
        /// Busca o usuário pelo ID ou lança erro 404 se não encontrado
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        /// Define a data atual para quando o avatar foi desbloqueado
        avatarUsuario.setDesbloqueadoEm(LocalDateTime.now());

        /// Associa o usuário ao avatar criado
        avatarUsuario.setUsuario(usuario);

        /// Salva e retorna o avatar criado no banco
        return avatarUsuarioRepository.save(avatarUsuario);
    }

    /**
     * Edita um avatar associado a um usuário.
     */
    @PutMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuario editarAvatarUsuario(@PathVariable Integer idUsuario,
                                             @PathVariable Integer idAvatarUsuario,
                                             @RequestBody AvatarUsuario avatarUsuario) {
        /// Busca o avatar a ser alterado ou lança erro 404
        AvatarUsuario alterar = avatarUsuarioRepository.findById(idAvatarUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        /// Verifica se o avatar pertence ao usuário; se não, lança erro 400
        if (!alterar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        /// Atualiza o avatar com os dados recebidos
        alterar.setAvatar(avatarUsuario.getAvatar());

        /// Salva e retorna o avatar atualizado
        return avatarUsuarioRepository.save(alterar);
    }

    /**
     * Remove um avatar associado a um usuário.
     */
    @DeleteMapping("/{idUsuario}/avatares/{idAvatarUsuario}")
    public AvatarUsuario removerAvatarUsuario(@PathVariable Integer idUsuario,
                                              @PathVariable Integer idAvatarUsuario) {
        /// Busca o avatar pelo ID ou lança erro 404
        AvatarUsuario avatar = avatarUsuarioRepository.findById(idAvatarUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        /// Verifica se o avatar pertence ao usuário; se não, lança erro 400
        if (!avatar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        /// Deleta o avatar do banco
        avatarUsuarioRepository.delete(avatar);

        /// Retorna o avatar removido (opcional)
        return avatar;
    }

    ///* ---------- Estoque Virtual do Usuário ---------- */

    /**
     * Lista todos os ingredientes associados a um usuário (estoque virtual).
     */
    @GetMapping("/{idUsuario}/estoque-virtual")
    public List<IngredienteUsuario> listarIngredienteUsuario(@PathVariable Integer idUsuario) {
        /// Retorna todos os ingredientes do usuário pelo ID
        return ingredienteUsuarioRepository.findByUsuarioId(idUsuario);
    }

    /**
     * Busca um ingrediente específico do estoque virtual de um usuário.
     */
    @GetMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuario buscarIngredienteUsuario(@PathVariable Integer idUsuario,
                                                       @PathVariable Integer idIngredienteUsuario) {
        /// Busca o ingrediente pelo ID ou lança erro 404 se não existir
        IngredienteUsuario estoqueVirtual = ingredienteUsuarioRepository.findById(idIngredienteUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        /// Verifica se o ingrediente pertence ao usuário; se não, lança erro 400
        if (!estoqueVirtual.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        /// Retorna o ingrediente válido
        return estoqueVirtual;
    }

    /**
     * Cria um novo ingrediente no estoque virtual associado a um usuário.
     */
    @PostMapping("/{idUsuario}/estoque-virtual")
    public IngredienteUsuario criarIngredienteUsuario(@PathVariable Integer idUsuario,
                                                      @RequestBody IngredienteUsuario ingredienteUsuario) {
        /// Busca o usuário pelo ID ou lança erro 404 se não encontrado
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        /// Define a data atual para quando o ingrediente foi adicionado
        ingredienteUsuario.setDataAdicionada(LocalDateTime.now());

        /// Associa o usuário ao ingrediente criado
        ingredienteUsuario.setUsuario(usuario);

        /// Salva e retorna o ingrediente criado no banco
        return ingredienteUsuarioRepository.save(ingredienteUsuario);
    }

    /**
     * Edita um ingrediente do estoque virtual associado a um usuário.
     */
    @PutMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuario editarIngredienteUsuario(@PathVariable Integer idUsuario,
                                                       @PathVariable Integer idIngredienteUsuario,
                                                       @RequestBody IngredienteUsuario ingredienteUsuario) {
        /// Busca o ingrediente a ser alterado ou lança erro 404
        IngredienteUsuario alterar = ingredienteUsuarioRepository.findById(idIngredienteUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        /// Verifica se o ingrediente pertence ao usuário; se não, lança erro 400
        if (!alterar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        /// Atualiza o ingrediente com os dados recebidos
        alterar.setIngrediente(ingredienteUsuario.getIngrediente());

        /// Salva e retorna o ingrediente atualizado
        return ingredienteUsuarioRepository.save(alterar);
    }

    /**
     * Remove um ingrediente do estoque virtual associado a um usuário.
     */
    @DeleteMapping("/{idUsuario}/estoque-virtual/{idIngredienteUsuario}")
    public IngredienteUsuario removerIngredienteUsuario(@PathVariable Integer idUsuario,
                                                        @PathVariable Integer idIngredienteUsuario) {
        /// Busca o ingrediente pelo ID ou lança erro 404
        IngredienteUsuario ingrediente = ingredienteUsuarioRepository.findById(idIngredienteUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        /// Verifica se o ingrediente pertence ao usuário; se não, lança erro 400
        if (!ingrediente.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        /// Deleta o ingrediente do banco
        ingredienteUsuarioRepository.delete(ingrediente);

        /// Retorna o ingrediente removido (opcional)
        return ingrediente;
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
    public ProgressoUsuarioResponse editarProgressoUsuario(@PathVariable Integer idUsuario,
                                                   @RequestBody ProgressoUsuarioRequest request) {
        return progressoUsuarioService.alterar(idUsuario, request);
    }

    ///* ---------- Receitas Concluídas pelo Usuário ---------- */
    /**
     * Lista todas as receitas concluídas de um usuário.
     */
    @GetMapping("/{idUsuario}/receitas")
    public List<ReceitaUsuario> listarReceitaUsuario(@PathVariable Integer idUsuario) {
        // Retorna todas as receitas associadas ao usuário pelo ID
        return receitaUsuarioRepository.findByUsuarioId(idUsuario);
    }

    /**
     * Busca uma receita concluída específica de um usuário pelo ID da receita.
     */
    @GetMapping("/{idUsuario}/receitas/{idReceitaUsuario}")
    public ReceitaUsuario buscarReceitaUsuario(@PathVariable Integer idUsuario,
                                               @PathVariable Integer idReceitaUsuario) {
        /// Busca receita concluída pelo id da receita
        ReceitaUsuario receita = receitaUsuarioRepository.findById(idReceitaUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita do usuário não encontrada"));

        /// Verifica se a receita pertence ao usuário solicitado
        if (!receita.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não realizada pelo usuário");
        }

        return receita;
    }

    /**
     * Adiciona uma nova receita concluída pelo usuário.
     */
    @PostMapping("/{idUsuario}/receitas")
    public ReceitaUsuario adicionarReceitaUsuario(@PathVariable Integer idUsuario,
                                                  @RequestBody ReceitaUsuario receitaUsuario) {
        /// Busca usuário pelo ID ou retorna 404
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        /// Busca receita original pelo ID recebido no corpo da requisição
        Receita receita = receitaRepository.findById(receitaUsuario.getReceita().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        /// Associa usuário e receita ao objeto ReceitaUsuario
        receitaUsuario.setUsuario(usuario);
        receitaUsuario.setReceita(receita);

        /// Define a data atual como conclusão
        receitaUsuario.setDataConclusao(LocalDateTime.now());

        /// Salva e retorna o objeto receitaUsuario persistido
        return receitaUsuarioRepository.save(receitaUsuario);
    }

    /**
     * Remove uma receita concluída associada a um usuário.
     */
    @DeleteMapping("/{idUsuario}/receitas/{idReceitaUsuario}")
    public ReceitaUsuario removerReceitaUsuario(@PathVariable Integer idUsuario,
                                                @PathVariable Integer idReceitaUsuario) {
        /// Busca a receita concluída pelo idReceita
        ReceitaUsuario receitaUsuario = receitaUsuarioRepository.findById(idReceitaUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        /// Verifica se a receita pertence ao usuário informado
        if (!receitaUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não realizada pelo usuário");
        }

        /// Remove a receita concluída do banco
        receitaUsuarioRepository.delete(receitaUsuario);

        /// Retorna a receita removida como confirmação
        return receitaUsuario;
    }

    ///* ---------- Títulos do Usuário ---------- */

    /**
     * Lista todos os títulos associados a um usuário pelo ID.
     */
    @GetMapping("/{idUsuario}/titulos")
    public List<TituloUsuario> listarTitulosUsuario(@PathVariable Integer idUsuario) {

        /// Retorna todos os títulos do usuário usando o repositório
        return tituloUsuarioRepository.findByUsuarioId(idUsuario);
    }

    /**
     * Busca um título específico de um usuário.
     */
    @GetMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuario buscarTituloUsuario(@PathVariable Integer idUsuario,
                                          @PathVariable Integer idTituloUsuario) {

        /// Busca o título pelo ID ou lança erro 404 se não existir
        TituloUsuario titulo = tituloUsuarioRepository.findById(idTituloUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        /// Verifica se o título pertence ao usuário; se não, lança erro 400
        if (!titulo.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pertence ao usuário");
        }

        /// Retorna o título válido
        return titulo;
    }

    /**
     * Cria um novo título associado a um usuário.
     */
    @PostMapping("/{idUsuario}/titulos")
    public TituloUsuario criarTituloUsuario(@PathVariable Integer idUsuario,
                                                @RequestBody TituloUsuario tituloUsuario) {

        /// Busca o usuário pelo ID ou lança erro 404 se não existir
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        /// Associa o usuário ao novo título
        tituloUsuario.setUsuario(usuario);

        /// Data de desbloqueio
        tituloUsuario.setDesbloqueadoEm(LocalDateTime.now());

        /// Salva e retorna o título criado no banco
        return tituloUsuarioRepository.save(tituloUsuario);
    }

    /**
     * Edita um título associado a um usuário.
     */
    @PutMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuario editarTituloUsuario(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idTituloUsuario,
                                                 @RequestBody TituloUsuario tituloUsuario) {
        /// Busca o título a ser alterado ou lança erro 404
        TituloUsuario alterar = tituloUsuarioRepository.findById(idTituloUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        /// Verifica se o título pertence ao usuário; se não, lança erro 400
        if (!alterar.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pertence ao usuário");
        }

        /// Atualiza o campo título com os dados recebidos
        alterar.setTitulo(tituloUsuario.getTitulo());

        /// Salva e retorna o título atualizado
        return tituloUsuarioRepository.save(alterar);
    }

    /**
     * Remove um título associado a um usuário.
     */
    @DeleteMapping("/{idUsuario}/titulos/{idTituloUsuario}")
    public TituloUsuario removerTituloUsuario(@PathVariable Integer idUsuario,
                                                  @PathVariable Integer idTituloUsuario) {
        /// Busca o título pelo ID ou lança erro 404
        TituloUsuario tituloUsuario = tituloUsuarioRepository.findById(idTituloUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        /// Verifica se o título pertence ao usuário; se não, lança erro 400
        if (!tituloUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pertence ao usuário");
        }

        /// Deleta o título do banco
        tituloUsuarioRepository.delete(tituloUsuario);

        /// Retorna o adjetivo removido (opcional)
        return tituloUsuario;
    }
}
