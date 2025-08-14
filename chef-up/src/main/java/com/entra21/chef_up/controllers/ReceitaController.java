package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaRequest;
import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaRequest;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaResponse;
import com.entra21.chef_up.dtos.Receita.ReceitaRequest;
import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaRequest;
import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaResponse;
import com.entra21.chef_up.entities.*;
import com.entra21.chef_up.repositories.*;
import com.entra21.chef_up.services.EtapaReceitaService;
import com.entra21.chef_up.services.IngredienteReceitaService;
import com.entra21.chef_up.services.ReceitaService;
import com.entra21.chef_up.services.UtensilioReceitaService;
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
    private final IngredienteReceitaService ingredienteReceitaService;
    private final UtensilioReceitaService utensilioReceitaService;

    /// Construtor com injeção de dependência dos repositórios
    public ReceitaController(ReceitaRepository receitaRepository,
                             EtapaReceitaRepository etapaReceitaRepository,
                             UtensilioReceitaRepository utensilioReceitaRepository,
                             IngredienteReceitaRepository ingredienteReceitaRepository,
                             ReceitaService receitaService, EtapaReceitaService etapaReceitaService, IngredienteReceitaService ingredienteReceitaService, UtensilioReceitaService utensilioReceitaService) {
        this.receitaRepository = receitaRepository;
        this.etapaReceitaRepository = etapaReceitaRepository;
        this.utensilioReceitaRepository = utensilioReceitaRepository;
        this.ingredienteReceitaRepository = ingredienteReceitaRepository;
        this.receitaService = receitaService;
        this.etapaReceitaService = etapaReceitaService;
        this.ingredienteReceitaService = ingredienteReceitaService;
        this.utensilioReceitaService = utensilioReceitaService;
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
     * @param request   novos dados da etapa
     * @return etapa atualizada
     */
    @PutMapping("/{idReceita}/etapas/{idEtapaReceita}")
    public EtapaReceitaResponse editarEtapaReceita(@PathVariable Integer idReceita,
                                           @PathVariable Integer idEtapaReceita,
                                           @RequestBody EtapaReceitaRequest request) {
        return etapaReceitaService.alterar(idReceita, idEtapaReceita, request);
    }

    ///* ---------- Utensílios da Receita ---------- */

    /**
     * Lista todos os utensílios associados a uma receita pelo ID.
     */
    @GetMapping("/{idReceita}/utensilios")
    public List<UtensilioReceitaResponse> listarUtensilioReceita(@PathVariable Integer idReceita) {
        return utensilioReceitaService.listarTodos(idReceita);
    }

    /**
     * Busca um utensílio específico de uma receita.
     */
    @GetMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceitaResponse buscarUtensilioReceita(@PathVariable Integer idReceita,
                                                   @PathVariable Integer idUtensilioReceita) {
    return utensilioReceitaService.buscarPorId(idReceita, idUtensilioReceita);
    }

    /**
     * Cria um novo utensílio associado a uma receita.
     */
    @PostMapping("/{idReceita}/utensilios")
    public UtensilioReceitaResponse criarUtensilioReceita(@PathVariable Integer idReceita,
                                                  @RequestBody UtensilioReceitaRequest request) {
return utensilioReceitaService.criar(idReceita, request);
    }

    /**
     * Edita um utensílio associado a uma receita.
     */
    @PutMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceitaResponse editarUtensilioReceita(@PathVariable Integer idReceita,
                                                   @PathVariable Integer idUtensilioReceita,
                                                   @RequestBody UtensilioReceitaRequest request) {
        return utensilioReceitaService.alterar(idReceita, idUtensilioReceita, request);
    }

    /**
     * Remove um utensílio associado a uma receita.
     */
    @DeleteMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceitaResponse removerUtensilioReceita(@PathVariable Integer idReceita,
                                                    @PathVariable Integer idUtensilioReceita) {
        return utensilioReceitaService.remover(idReceita, idUtensilioReceita);
    }

    ///* ---------- Ingredientes da Receita ---------- */

    /**
     * Lista todos os ingredientes associados a uma receita pelo ID.
     */
    @GetMapping("/{idReceita}/ingredientes")
    public List<IngredienteReceitaResponse> listarIngredienteReceita(@PathVariable Integer idReceita) {
        return ingredienteReceitaService.listarTodos(idReceita);
    }

    /**
     * Busca um ingrediente específico de uma receita.
     */
    @GetMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceitaResponse buscarIngredienteReceita(@PathVariable Integer idReceita,
                                                       @PathVariable Integer idIngredienteReceita) {
        return ingredienteReceitaService.buscarPorId(idReceita, idIngredienteReceita);
    }

    /**
     * Cria um novo ingrediente associado a uma receita.
     */
    @PostMapping("/{idReceita}/ingredientes")
    public IngredienteReceitaResponse criarIngredienteReceita(@PathVariable Integer idReceita,
                                                      @RequestBody IngredienteReceitaRequest request) {
        return ingredienteReceitaService.criar(idReceita,request);
    }

    /**
     * Edita um ingrediente associado a uma receita.
     */
    @PutMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceitaResponse editarIngredienteReceita(@PathVariable Integer idReceita,
                                                       @PathVariable Integer idIngredienteReceita,
                                                       @RequestBody IngredienteReceitaRequest request) {
        return ingredienteReceitaService.alterar(idReceita, idIngredienteReceita, request);
    }

    /**
     * Remove um ingrediente associado a uma receita.
     */
    @DeleteMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceitaResponse removerIngredienteReceita(@PathVariable Integer idReceita,
                                                        @PathVariable Integer idIngredienteReceita) {
        return ingredienteReceitaService.remover(idReceita, idIngredienteReceita);
    }
}
