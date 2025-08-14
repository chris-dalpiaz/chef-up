package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaRequest;
import com.entra21.chef_up.dtos.UtensilioReceita.UtensilioReceitaResponse;
import com.entra21.chef_up.entities.*;
import com.entra21.chef_up.repositories.ReceitaRepository;
import com.entra21.chef_up.repositories.UtensilioReceitaRepository;
import com.entra21.chef_up.repositories.UtensilioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UtensilioReceitaService {

    private final UtensilioRepository utensilioRepository;
    private final ReceitaRepository receitaRepository;
    private final UtensilioReceitaRepository utensilioReceitaRepository;
    private final ModelMapper modelMapper;

    public UtensilioReceitaService(UtensilioRepository utensilioRepository, ReceitaRepository receitaRepository, UtensilioReceitaRepository utensilioReceitaRepository, ModelMapper modelMapper) {
        this.utensilioRepository = utensilioRepository;
        this.receitaRepository = receitaRepository;
        this.utensilioReceitaRepository = utensilioReceitaRepository;
        this.modelMapper = modelMapper;
    }

    public List<UtensilioReceitaResponse> listarTodos(Integer idReceita) {
        return utensilioReceitaRepository.findByReceitaId(idReceita)
                .stream()
                .map(etapa -> modelMapper.map(etapa, UtensilioReceitaResponse.class))
                .toList();
    }

    public UtensilioReceitaResponse buscarPorId(Integer idReceita, Integer idUtensilioReceita) {
        UtensilioReceita utensilioReceita = utensilioReceitaRepository.findById(idUtensilioReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        // Verifica se a etapa pertence à receita informada
        if (!utensilioReceita.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utensílio não pertence à receita informada");
        }

        return modelMapper.map(utensilioReceita, UtensilioReceitaResponse.class);
    }


    @Transactional
    public UtensilioReceitaResponse criar(Integer idReceita, UtensilioReceitaRequest request) {
        // Verifica se a receita existe
        Receita receita = receitaRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        // Verifica se o ingrediente existe
        Utensilio utensilio = utensilioRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        // Cria nova etapa
        UtensilioReceita novoUtensilioReceita = new UtensilioReceita();
        novoUtensilioReceita.setReceita(receita);
        novoUtensilioReceita.setUtensilio(utensilio);

        // Salva e retorna
        UtensilioReceita salva = utensilioReceitaRepository.save(novoUtensilioReceita);
        return modelMapper.map(salva, UtensilioReceitaResponse.class);
    }

    @Transactional
    public UtensilioReceitaResponse alterar(Integer idReceita, Integer idUtensilioReceita, UtensilioReceitaRequest request) {
        // Busca a etapa existente
        UtensilioReceita utensilioReceitaExistente = utensilioReceitaRepository.findById(idUtensilioReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio da Receita não encontrado"));

         Utensilio utensilio = utensilioRepository.findById(request.getIdUtensilio())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo utensílio não encontrado"));

        // Verifica se a etapa pertence à receita informada
        if (!utensilioReceitaExistente.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utensílio não pertence à receita informada");
        }

        // Atualiza os campos
        utensilioReceitaExistente.setUtensilio(utensilio);

        // Salva e retorna a entidade atualizada
        utensilioReceitaRepository.save(utensilioReceitaExistente);
        return modelMapper.map(utensilioReceitaExistente, UtensilioReceitaResponse.class);

    }

    @Transactional
    public UtensilioReceitaResponse remover(Integer idReceita, Integer idUtensilioReceita) {
        UtensilioReceita utensilioReceita = utensilioReceitaRepository.findById(idUtensilioReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        // Verifica se o ingrediente pertence à receita informada
        if (!utensilioReceita.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utensílio não pertence à receita informada");
        }

        utensilioReceitaRepository.deleteById(idUtensilioReceita);

        return modelMapper.map(utensilioReceita, UtensilioReceitaResponse.class);
    }
}
