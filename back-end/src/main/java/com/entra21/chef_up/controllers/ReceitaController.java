package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.IngredienteEtapaReceita.IngredienteEtapaReceitaRequest;
import com.entra21.chef_up.dtos.IngredienteEtapaReceita.IngredienteEtapaReceitaResponse;
import com.entra21.chef_up.dtos.Receita.ReceitaRequest;
import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaRequest;
import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaRequest;
import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaResponse;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaRequest;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaResponse;
import com.entra21.chef_up.repositories.*;
import com.entra21.chef_up.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;
    private final EtapaReceitaService etapaReceitaService;
    private final IngredienteReceitaService ingredienteReceitaService;
    private final UtensilioReceitaService utensilioReceitaService;
    private final IngredienteEtapaReceitaService ingredienteEtapaReceitaService;

    public ReceitaController(ReceitaRepository receitaRepository,
                             EtapaReceitaRepository etapaReceitaRepository,
                             UtensilioReceitaRepository utensilioReceitaRepository,
                             IngredienteReceitaRepository ingredienteReceitaRepository,
                             ReceitaService receitaService,
                             EtapaReceitaService etapaReceitaService,
                             IngredienteReceitaService ingredienteReceitaService,
                             UtensilioReceitaService utensilioReceitaService, IngredienteEtapaReceitaService ingredienteEtapaReceitaService) {
        this.receitaService = receitaService;
        this.etapaReceitaService = etapaReceitaService;
        this.ingredienteReceitaService = ingredienteReceitaService;
        this.utensilioReceitaService = utensilioReceitaService;
        this.ingredienteEtapaReceitaService = ingredienteEtapaReceitaService;
    }

    /**
     * Lista todas as receitas cadastradas.
     */
    @GetMapping
    public List<ReceitaResponse> listRecipes() {
        return receitaService.listAll();
    }

    /**
     * Busca uma receita pelo ID.
     */
    @GetMapping("/{idReceita}")
    public ReceitaResponse getRecipe(@PathVariable Integer idReceita) {
        return receitaService.getById(idReceita);
    }

    /**
     * Cria uma nova receita.
     */
    @PostMapping
    public ReceitaResponse createRecipe(@RequestBody ReceitaRequest request) {
        return receitaService.create(request);
    }

    /**
     * Atualiza os dados de uma receita existente.
     */
    @PutMapping("/{idReceita}")
    public ReceitaResponse updateRecipe(@PathVariable Integer idReceita,
                                        @RequestBody ReceitaRequest request) {
        return receitaService.update(idReceita, request);
    }

    /**
     * Remove uma receita pelo ID.
     */
    @DeleteMapping("/{idReceita}")
    public ReceitaResponse deleteRecipe(@PathVariable Integer idReceita) {
        return receitaService.delete(idReceita);
    }

    /* ---------- Etapas da Receita ---------- */

    @GetMapping("/{idReceita}/etapas")
    public List<EtapaReceitaResponse> listRecipeSteps(@PathVariable Integer idReceita) {
        return etapaReceitaService.listByRecipe(idReceita);
    }

    @GetMapping("/{idReceita}/etapas/{idEtapaReceita}")
    public EtapaReceitaResponse getRecipeStep(@PathVariable Integer idReceita,
                                              @PathVariable Integer idEtapaReceita) {
        return etapaReceitaService.getById(idReceita, idEtapaReceita);
    }

    @PostMapping("/{idReceita}/etapas")
    public EtapaReceitaResponse addRecipeStep(@PathVariable Integer idReceita,
                                              @RequestBody EtapaReceitaRequest request) {
        return etapaReceitaService.create(idReceita, request);
    }

    @PutMapping("/{idReceita}/etapas/{idEtapaReceita}")
    public EtapaReceitaResponse updateRecipeStep(@PathVariable Integer idReceita,
                                                 @PathVariable Integer idEtapaReceita,
                                                 @RequestBody EtapaReceitaRequest request) {
        return etapaReceitaService.update(idReceita, idEtapaReceita, request);
    }

    /* ---------- Utensílios da Receita ---------- */

    @GetMapping("/{idReceita}/utensilios")
    public List<UtensilioReceitaResponse> listRecipeUtensils(@PathVariable Integer idReceita) {
        return utensilioReceitaService.listByRecipe(idReceita);
    }

    @GetMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceitaResponse getRecipeUtensil(@PathVariable Integer idReceita,
                                                     @PathVariable Integer idUtensilioReceita) {
        return utensilioReceitaService.getById(idReceita, idUtensilioReceita);
    }

    @PostMapping("/{idReceita}/utensilios")
    public UtensilioReceitaResponse createRecipeUtensil(@PathVariable Integer idReceita,
                                                        @RequestBody UtensilioReceitaRequest request) {
        return utensilioReceitaService.create(idReceita, request);
    }

    @PutMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceitaResponse updateRecipeUtensil(@PathVariable Integer idReceita,
                                                        @PathVariable Integer idUtensilioReceita,
                                                        @RequestBody UtensilioReceitaRequest request) {
        return utensilioReceitaService.update(idReceita, idUtensilioReceita, request);
    }

    @DeleteMapping("/{idReceita}/utensilios/{idUtensilioReceita}")
    public UtensilioReceitaResponse deleteRecipeUtensil(@PathVariable Integer idReceita,
                                                        @PathVariable Integer idUtensilioReceita) {
        return utensilioReceitaService.delete(idReceita, idUtensilioReceita);
    }

    /* ---------- Ingredientes da Receita ---------- */

    @GetMapping("/{idReceita}/ingredientes")
    public List<IngredienteReceitaResponse> listRecipeIngredients(@PathVariable Integer idReceita) {
        return ingredienteReceitaService.listByRecipe(idReceita);
    }

    @GetMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceitaResponse getRecipeIngredient(@PathVariable Integer idReceita,
                                                          @PathVariable Integer idIngredienteReceita) {
        return ingredienteReceitaService.getById(idReceita, idIngredienteReceita);
    }

    @PostMapping("/{idReceita}/ingredientes")
    public IngredienteReceitaResponse createRecipeIngredient(@PathVariable Integer idReceita,
                                                             @RequestBody IngredienteReceitaRequest request) {
        return ingredienteReceitaService.create(idReceita, request);
    }

    @PutMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceitaResponse updateRecipeIngredient(@PathVariable Integer idReceita,
                                                             @PathVariable Integer idIngredienteReceita,
                                                             @RequestBody IngredienteReceitaRequest request) {
        return ingredienteReceitaService.update(idReceita, idIngredienteReceita, request);
    }

    @DeleteMapping("/{idReceita}/ingredientes/{idIngredienteReceita}")
    public IngredienteReceitaResponse deleteRecipeIngredient(@PathVariable Integer idReceita,
                                                             @PathVariable Integer idIngredienteReceita) {
        return ingredienteReceitaService.delete(idReceita, idIngredienteReceita);
    }

    /* ---------- Ingredientes da Etapa da Receita ---------- */

    @GetMapping("/{idReceita}/etapas/{idEtapaReceita}/ingredientes")
    public List<IngredienteEtapaReceitaResponse> listRecipeStepIngredients(@PathVariable Integer idReceita,
                                                                       @PathVariable Integer idEtapaReceita) {
        return ingredienteEtapaReceitaService.listIngredientsByStep(idReceita, idEtapaReceita);
    }

    @GetMapping("/{idReceita}/etapas/{idEtapaReceita}/ingredientes/{idIngrediente}")
    public IngredienteEtapaReceitaResponse getRecipeStepIngredient(@PathVariable Integer idReceita,
                                                               @PathVariable Integer idEtapaReceita,
                                                               @PathVariable Integer idIngrediente) {
        return ingredienteEtapaReceitaService.getById(idReceita, idEtapaReceita, idIngrediente);
    }

    @PostMapping("/{idReceita}/etapas/{idEtapaReceita}/ingredientes")
    public IngredienteEtapaReceitaResponse createRecipeStepIngredient(@PathVariable Integer idReceita,
                                                                      @PathVariable Integer idEtapaReceita,
                                                                      @RequestBody IngredienteEtapaReceitaRequest request) {
        return ingredienteEtapaReceitaService.create(idReceita, idEtapaReceita, request);
    }

    @PutMapping("/{idReceita}/etapas/{idEtapaReceita}/ingredientes/{idIngrediente}")
    public IngredienteEtapaReceitaResponse updateRecipeStepIngredient(@PathVariable Integer idReceita,
                                                             @PathVariable Integer idEtapaReceita,
                                                             @PathVariable Integer idIngrediente,
                                                             @RequestBody IngredienteEtapaReceitaRequest request) {
        return ingredienteEtapaReceitaService.update(idReceita, idEtapaReceita, idIngrediente, request);
    }

    @DeleteMapping("/{idReceita}/etapas/{idEtapaReceita}/ingredientes/{idIngrediente}")
    public IngredienteEtapaReceitaResponse deleteRecipeStepIngredient(@PathVariable Integer idReceita,
                                                                      @PathVariable Integer idEtapaReceita,
                                                                      @PathVariable Integer idIngrediente) {
        return ingredienteEtapaReceitaService.delete(idReceita, idEtapaReceita, idIngrediente);
    }
}
