package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaRequest;
import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.dtos.Receita.ReceitaRequest;
import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import com.entra21.chef_up.entities.*;
import com.entra21.chef_up.repositories.*;
import com.entra21.chef_up.services.EtapaReceitaService;
import com.entra21.chef_up.services.ReceitaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaRepository receitaRepository;
    private final EtapaReceitaRepository etapaReceitaRepository;
    private final UtensilioReceitaRepository utensilioReceitaRepository;
    private final IngredienteReceitaRepository ingredienteReceitaRepository;
    private final ReceitaService receitaService;
    private final EtapaReceitaService etapaReceitaService;

    /// Construtor com injeção de dependência dos repositórios
    public ReceitaController(ReceitaRepository receitaRepository,
                             EtapaReceitaRepository etapaReceitaRepository,
                             UtensilioReceitaRepository utensilioReceitaRepository,
                             IngredienteReceitaRepository ingredienteReceitaRepository,
                             ReceitaService receitaService, EtapaReceitaService etapaReceitaService) {
        this.receitaRepository = receitaRepository;
        this.etapaReceitaRepository = etapaReceitaRepository;
        this.utensilioReceitaRepository = utensilioReceitaRepository;
        this.ingredienteReceitaRepository = ingredienteReceitaRepository;
        this.receitaService = receitaService;
        this.etapaReceitaService = etapaReceitaService;
    }

    /**
     * Lista todas as receitas cadastradas.
     *
     * @return lista de receitas
     */
    @GetMapping
    public List<ReceitaResponse> listarReceitas() {
        return receitaService.listarTodos();
    }

    /**
     * Busca uma receita pelo ID.
     * Retorna 404 se não for encontrada.
     *
     * @param idReceita ID da receita na URL
     * @return receita encontrada
     */
    @GetMapping("/{idReceita}")
    public ReceitaResponse buscarReceita(@PathVariable Integer idReceita) {
        return receitaService.buscar(idReceita);
    }

    /**
     * Cria uma nova receita.
     *
     * @param request dados da receita no corpo da requisição
     * @return receita criada
     */
    @PostMapping
    public ReceitaResponse criarReceita(@RequestBody ReceitaRequest request) {
        return receitaService.criar(request);
    }

    /**
     * Atualiza os dados de uma receita existente.
     * Retorna 404 se não existir.
     *
     * @param idReceita ID da receita a alterar
     * @param request   novos dados da receita
     * @return receita atualizada
     */
    @PutMapping("/{idReceita}")
    public ReceitaResponse alterarReceita(
            @PathVariable Integer idReceita,
            @RequestBody ReceitaRequest request
    ) {
        return receitaService.alterar(idReceita, request);
    }

    /**
     * Remove uma receita pelo ID.
     * Retorna a receita removida ou erro 404 se não existir.
     *
     * @param idReceita ID da receita para remover
     * @return receita removida
     */
    @DeleteMapping("/{idReceita}")
    public ReceitaResponse removerReceita(@PathVariable Integer idReceita) {
        return receitaService.remover(idReceita);
    }

    ///* ---------- Etapas da Receita ---------- */

    /**
     * Lista todas as etapas associadas a uma receita.
     *
     * @param idReceita ID da receita
     * @return lista de etapas
     */

    @GetMapping("/{idReceita}/etapas")
    public List<EtapaReceitaResponse> listarEtapaReceita(@PathVariable Integer idReceita) {
        return etapaReceitaService.listarTodos(idReceita);
    }


    /**
     * Busca uma etapa específica de uma receita pelo ID.
     * Retorna 404 se não encontrada ou 400 se etapa não pertence à receita.
     *
     * @param idReceita      ID da receita
     * @param idEtapaReceita ID da etapa
     * @return etapa encontrada
     */
    @GetMapping("/{idReceita}/etapas/{idEtapaReceita}")
    public EtapaReceitaResponse buscarEtapaReceita(@PathVariable Integer idReceita,
                                           @PathVariable Integer idEtapaReceita) {
        return etapaReceitaService.buscarPorId(idReceita, idEtapaReceita);
    }

    /**
     * Cria uma nova etapa para a receita.
     * Define ordem incrementando o maior valor atual.
     *
     * @param idReceita    ID da receita
     * @param request dados da etapa no corpo da requisição
     * @return etapa criada
     */
    @PostMapping("/{idReceita}/etapas")
    public EtapaReceitaResponse adicionarEtapa(@PathVariable Integer idReceita, @RequestBody EtapaReceitaRequest request) {
        return etapaReceitaService.criar(idReceita, request);
    }

    /**
     * Edita uma etapa associada a uma receita.
     * Verifica se etapa pertence à receita.
     *
     * @param idReceita      ID da receita
     * @param idEtapaReceita ID da etapa
     * @param etapaReceita   novos dados da etapa
     * @return etapa atualizada
     */
    @PutMapping("/{idReceita}/etapas/{idEtapaReceita}")
    public EtapaReceita editarEtapaReceita(@PathVariable Integer idReceita,
                                           @PathVariable Integer idEtapaReceita,
                                           @RequestBody EtapaReceita etapaReceita) {
        return etapaReceitaService.alterar(idReceita, idEtapaReceita, etapaReceita);
    }

    ///* ---------- Utensílios da Receita ---------- */

    /**
     * Lista todos os utensílios associados a uma receita pelo ID.
     */
    @GetMapping("/{idReceita}/utensilios")
    public List<UtensilioReceita> listarUtensilioReceita(@PathVariable Integer idReceita) {

        /// Retorna todos os utensílios da receita usando o repositório
        return utensilioReceitaRepository.findByReceitaId(idReceita);
    }

    /**
     * Busca um utensílio específico de uma receita.
     */
    @GetMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceita buscarUtensilioReceita(@PathVariable Integer idReceita,
                                                   @PathVariable Integer idUtensilioReceita) {

        /// Busca o utensílio pelo ID ou lança erro 404 se não existir
        UtensilioReceita utensilioReceita = utensilioReceitaRepository.findById(idUtensilioReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        /// Verifica se o utensílio pertence a receita; se não, lança erro 400
        if (!utensilioReceita.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utensílio não pertence a receita");
        }

        /// Retorna o utensílio válido
        return utensilioReceita;
    }

    /**
     * Cria um novo utensílio associado a uma receita.
     */
    @PostMapping("/{idReceita}/utensilios")
    public UtensilioReceita criarUtensilioReceita(@PathVariable Integer idReceita,
                                                  @RequestBody UtensilioReceita utensilioReceita) {

        /// Busca a receita pelo ID ou lança erro 404 se não existir
        Receita receita = receitaRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        /// Associa a receita ao novo utensílio
        utensilioReceita.setReceita(receita);

        /// Salva e retorna o utensílio criado no banco
        return utensilioReceitaRepository.save(utensilioReceita);
    }

    /**
     * Edita um utensílio associado a uma receita.
     */
    @PutMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceita editarUtensilioReceita(@PathVariable Integer idReceita,
                                                   @PathVariable Integer idUtensilioReceita,
                                                   @RequestBody UtensilioReceita utensilioReceita) {
        /// Busca o utensílio a ser alterado ou lança erro 404
        UtensilioReceita alterar = utensilioReceitaRepository.findById(idUtensilioReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        /// Verifica se o utensílio pertence a receita; se não, lança erro 400
        if (!alterar.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utensílio não pertence a receita");
        }

        /// Atualiza o campo utensílio com os dados recebidos
        alterar.setUtensilio(utensilioReceita.getUtensilio());

        /// Salva e retorna o utensílio atualizado
        return utensilioReceitaRepository.save(alterar);
    }

    /**
     * Remove um utensílio associado a uma receita.
     */
    @DeleteMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceita removerUtensilioReceita(@PathVariable Integer idReceita,
                                                    @PathVariable Integer idUtensilioReceita) {
        /// Busca o utensílio pelo ID ou lança erro 404
        UtensilioReceita utensilioReceita = utensilioReceitaRepository.findById(idUtensilioReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        /// Verifica se o utensílio pertence a receita; se não, lança erro 400
        if (!utensilioReceita.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utensílio não pertence a receita");
        }

        /// Deleta o utensílio do banco
        utensilioReceitaRepository.delete(utensilioReceita);

        /// Retorna o utensílio removido (opcional)
        return utensilioReceita;
    }

    ///* ---------- Ingredientes da Receita ---------- */

    /**
     * Lista todos os ingredientes associados a uma receita pelo ID.
     */
    @GetMapping("/{idReceita}/ingredientes")
    public List<IngredienteReceita> listarIngredienteReceita(@PathVariable Integer idReceita) {

        /// Retorna todos os utensílios da receita usando o repositório
        return ingredienteReceitaRepository.findByReceitaId(idReceita);
    }

    /**
     * Busca um ingrediente específico de uma receita.
     */
    @GetMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceita buscarIngredienteReceita(@PathVariable Integer idReceita,
                                                       @PathVariable Integer idIngredienteReceita) {

        /// Busca o ingrediente pelo ID ou lança erro 404 se não existir
        IngredienteReceita ingredienteReceita = ingredienteReceitaRepository.findById(idIngredienteReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        /// Verifica se o ingrediente pertence a receita; se não, lança erro 400
        if (!ingredienteReceita.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence a receita");
        }

        /// Retorna o ingrediente válido
        return ingredienteReceita;
    }

    /**
     * Cria um novo ingrediente associado a uma receita.
     */
    @PostMapping("/{idReceita}/ingredientes")
    public IngredienteReceita criarIngredienteReceita(@PathVariable Integer idReceita,
                                                      @RequestBody IngredienteReceita ingredienteReceita) {

        /// Busca a receita pelo ID ou lança erro 404 se não existir
        Receita receita = receitaRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        /// Associa a receita ao novo ingrediente
        ingredienteReceita.setReceita(receita);

        /// Salva e retorna o ingrediente criado no banco
        return ingredienteReceitaRepository.save(ingredienteReceita);
    }

    /**
     * Edita um ingrediente associado a uma receita.
     */
    @PutMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceita editarIngredienteReceita(@PathVariable Integer idReceita,
                                                       @PathVariable Integer idIngredienteReceita,
                                                       @RequestBody IngredienteReceita ingredienteReceita) {
        /// Busca o ingrediente a ser alterado ou lança erro 404
        IngredienteReceita alterar = ingredienteReceitaRepository.findById(idIngredienteReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        /// Verifica se o ingrediente pertence a receita; se não, lança erro 400
        if (!alterar.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence a receita");
        }

        /// Atualiza o campo igrediente com os dados recebidos
        alterar.setIngrediente(ingredienteReceita.getIngrediente());
        alterar.setQuantidade(ingredienteReceita.getQuantidade());
        alterar.setUnidadeMedida(ingredienteReceita.getUnidadeMedida());

        /// Salva e retorna o ingrediente atualizado
        return ingredienteReceitaRepository.save(alterar);
    }

    /**
     * Remove um ingrediente associado a uma receita.
     */
    @DeleteMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceita removerIngredienteReceita(@PathVariable Integer idReceita,
                                                        @PathVariable Integer idIngredienteReceita) {
        /// Busca o ingrediente pelo ID ou lança erro 404
        IngredienteReceita ingredienteReceita = ingredienteReceitaRepository.findById(idIngredienteReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        /// Verifica se o ingrediente pertence a receita; se não, lança erro 400
        if (!ingredienteReceita.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence a receita");
        }

        /// Deleta o ingrediente do banco
        ingredienteReceitaRepository.delete(ingredienteReceita);

        /// Retorna o ingrediente removido (opcional)
        return ingredienteReceita;
    }
}
