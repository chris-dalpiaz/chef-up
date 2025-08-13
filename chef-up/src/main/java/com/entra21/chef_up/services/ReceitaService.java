package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Receita.ReceitaRequest;
import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import com.entra21.chef_up.entities.Categoria;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ModelMapper modelMapper;
    private final CategoriaService categoriaService;
    private final IngredienteReceitaRepository ingredienteReceitaRepository;
    private final EtapaReceitaRepository etapaReceitaRepository;
    private final ReceitaUsuarioRepository receitaUsuarioRepository;
    private final ReceitaColecaoRepository receitaColecaoRepository;
    private final UtensilioReceitaRepository utensilioReceitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository, ModelMapper modelMapper, CategoriaService categoriaService, IngredienteReceitaRepository ingredienteReceitaRepository, EtapaReceitaRepository etapaReceitaRepository, ReceitaUsuarioRepository receitaUsuarioRepository, ReceitaColecaoRepository receitaColecaoRepository, UtensilioReceitaRepository utensilioReceitaRepository) {
        this.receitaRepository = receitaRepository;
        this.modelMapper = modelMapper;
        this.categoriaService = categoriaService;
        this.ingredienteReceitaRepository = ingredienteReceitaRepository;
        this.etapaReceitaRepository = etapaReceitaRepository;
        this.receitaUsuarioRepository = receitaUsuarioRepository;
        this.receitaColecaoRepository = receitaColecaoRepository;
        this.utensilioReceitaRepository = utensilioReceitaRepository;
    }

    public List<ReceitaResponse> listarTodos() {
        return receitaRepository.findAll().stream()
                .map(r -> {
                    ReceitaResponse receitaResponse = modelMapper.map(r, ReceitaResponse.class);

                    /// Mapear manualmente, pois o modelmapper não consegue listar objetos complexos
                    receitaResponse.setCategoria(categoriaService.mapParaResponse(r.getCategoria()));

                    return receitaResponse;
                })
                .toList();
    }

    public ReceitaResponse buscar(Integer id) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Receita não encontrada"));

        return modelMapper.map(receita, ReceitaResponse.class);
    }

    public ReceitaResponse criar(ReceitaRequest request) {
        /// Converte o DTO de requisição para a entidade
        Receita receita = modelMapper.map(request, Receita.class);

        /// Salva a entidade no banco de dados
        Receita salvo = receitaRepository.save(receita);

        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, ReceitaResponse.class);
    }

    public ReceitaResponse alterar(Integer id, ReceitaRequest request) {
        /// Busca pelo ID ou lança erro 404
        Receita alterar = receitaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Receita não encontrada"));

        alterar.setNome(request.getNome());
        alterar.setDificuldade(request.getDificuldade());
        alterar.setDescricao(request.getDescricao());
        alterar.setXpGanho(request.getXpGanho());
        alterar.setTempoPreparoSegundos(request.getTempoPreparoSegundos());

        Categoria categoria = categoriaService.buscarPorId(request.getIdCategoria());
        alterar.setCategoria(categoria);

        /// Salva a alteração no banco
        Receita salvo = receitaRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, ReceitaResponse.class);
    }

    @Transactional
    public ReceitaResponse remover(Integer id) {
        /// Busca pelo ID ou lança 404
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Receita não encontrada"));

        /// removendo dados que usam a fk da receita em outras tabelas, para poder removê-la
        ingredienteReceitaRepository.removeByReceitaId(id);
        etapaReceitaRepository.removeByReceitaId(id);
        receitaUsuarioRepository.removeByReceitaId(id);
        receitaColecaoRepository.removeByReceitaId(id);
        utensilioReceitaRepository.removeByReceitaId(id);


        receitaRepository.deleteById(id);

        /// Retorna o DTO do deletado
        return modelMapper.map(receita, ReceitaResponse.class);
    }
}
