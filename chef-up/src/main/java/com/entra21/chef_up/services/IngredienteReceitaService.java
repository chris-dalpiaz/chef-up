package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaRequest;
import com.entra21.chef_up.dtos.IngredienteReceita.IngredienteReceitaResponse;
import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.entities.IngredienteReceita;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.repositories.IngredienteReceitaRepository;
import com.entra21.chef_up.repositories.IngredienteRepository;
import com.entra21.chef_up.repositories.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngredienteReceitaService {

    private final IngredienteReceitaRepository ingredienteReceitaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final ReceitaRepository receitaRepository;
    private final ModelMapper modelMapper;

    public IngredienteReceitaService(IngredienteReceitaRepository ingredienteReceitaRepository, IngredienteRepository ingredienteRepository, ReceitaRepository receitaRepository, ModelMapper modelMapper) {
        this.ingredienteReceitaRepository = ingredienteReceitaRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.receitaRepository = receitaRepository;
        this.modelMapper = modelMapper;
    }


    public List<IngredienteReceitaResponse> listarTodos(Integer idReceita) {
        return ingredienteReceitaRepository.findByReceitaId(idReceita)
                .stream()
                .map(etapa -> modelMapper.map(etapa, IngredienteReceitaResponse.class))
                .toList();
    }

    public IngredienteReceitaResponse buscarPorId(Integer idReceita, Integer idIngredienteReceita) {
        IngredienteReceita ingredienteReceita = ingredienteReceitaRepository.findById(idIngredienteReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        // Verifica se a etapa pertence à receita informada
        if (!ingredienteReceita.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence à receita informada");
        }

        return modelMapper.map(ingredienteReceita, IngredienteReceitaResponse.class);
    }


    @Transactional
    public IngredienteReceitaResponse criar(Integer idReceita, IngredienteReceitaRequest request) {
        // Verifica se a receita existe
        Receita receita = receitaRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        // Verifica se o ingrediente existe
        Ingrediente ingrediente = ingredienteRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        // Cria nova etapa
        IngredienteReceita novoIngredienteReceita = new IngredienteReceita();
        novoIngredienteReceita.setReceita(receita);
        novoIngredienteReceita.setIngrediente(ingrediente);
        novoIngredienteReceita.setQuantidade(request.getQuantidade());
        novoIngredienteReceita.setUnidadeMedida(request.getUnidadeMedida());

        // Salva e retorna
        IngredienteReceita salva = ingredienteReceitaRepository.save(novoIngredienteReceita);
        return modelMapper.map(salva, IngredienteReceitaResponse.class);
    }

    @Transactional
    public IngredienteReceitaResponse alterar(Integer idReceita, Integer idIngredienteReceita, IngredienteReceitaRequest request) {
        // Busca a etapa existente
        IngredienteReceita ingredienteExistente = ingredienteReceitaRepository.findById(idIngredienteReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        // Verifica se a etapa pertence à receita informada
        if (!ingredienteExistente.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence à receita informada");
        }

        // Atualiza os campos
        ingredienteExistente.setUnidadeMedida(request.getUnidadeMedida());
        ingredienteExistente.setQuantidade(request.getQuantidade());

        // Salva e retorna a entidade atualizada
        ingredienteReceitaRepository.save(ingredienteExistente);
        return modelMapper.map(ingredienteExistente, IngredienteReceitaResponse.class);

    }

    @Transactional
    public IngredienteReceitaResponse remover(Integer idReceita, Integer idIngredienteReceita) {
        IngredienteReceita ingredienteReceita = ingredienteReceitaRepository.findById(idIngredienteReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        // Verifica se o ingrediente pertence à receita informada
        if (!ingredienteReceita.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence à receita informada");
        }

        ingredienteReceitaRepository.deleteById(idIngredienteReceita);

        return modelMapper.map(ingredienteReceita, IngredienteReceitaResponse.class);
    }

}
