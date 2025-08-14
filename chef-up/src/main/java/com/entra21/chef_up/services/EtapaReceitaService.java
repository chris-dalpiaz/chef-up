package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaRequest;
import com.entra21.chef_up.dtos.EtapaReceita.EtapaReceitaResponse;
import com.entra21.chef_up.entities.EtapaReceita;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.repositories.EtapaReceitaRepository;
import com.entra21.chef_up.repositories.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EtapaReceitaService {

    private final EtapaReceitaRepository etapaReceitaRepository;
    private final ReceitaRepository receitaRepository;
    private final ModelMapper modelMapper;

    public EtapaReceitaService(EtapaReceitaRepository etapaReceitaRepository, ReceitaRepository receitaRepository, ModelMapper modelMapper) {
        this.etapaReceitaRepository = etapaReceitaRepository;
        this.receitaRepository = receitaRepository;
        this.modelMapper = modelMapper;
    }

    public List<EtapaReceitaResponse> listarTodos(Integer idReceita) {
        return etapaReceitaRepository.findByReceitaId(idReceita)
                .stream()
                .map(etapa -> modelMapper.map(etapa, EtapaReceitaResponse.class))
                .toList();
    }

    public EtapaReceitaResponse buscarPorId(Integer idReceita, Integer idEtapaReceita) {
        EtapaReceita etapa = etapaReceitaRepository.findById(idEtapaReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrada"));

        // Verifica se a etapa pertence à receita informada
        if (!etapa.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Etapa não pertence à receita informada");
        }

        return modelMapper.map(etapa, EtapaReceitaResponse.class);
    }


    @Transactional
    public EtapaReceitaResponse criar(Integer idReceita, EtapaReceitaRequest etapaReceitaRequest) {
        // Verifica se a receita existe
        Receita receita = receitaRepository.findById(idReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        // Cria nova etapa
        EtapaReceita novaEtapa = new EtapaReceita();
        novaEtapa.setReceita(receita);
        novaEtapa.setOrdem(etapaReceitaRequest.getOrdem());
        novaEtapa.setConteudo(etapaReceitaRequest.getConteudo());

        // Salva e retorna
        EtapaReceita salva = etapaReceitaRepository.save(novaEtapa);
        return modelMapper.map(salva, EtapaReceitaResponse.class);
    }

    @Transactional
    public EtapaReceita alterar(Integer idReceita, Integer idEtapaReceita, EtapaReceita etapaReceitaRequest) {
        // Busca a etapa existente
        EtapaReceita etapaExistente = etapaReceitaRepository.findById(idEtapaReceita)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrada"));

        // Verifica se a etapa pertence à receita informada
        if (!etapaExistente.getReceita().getId().equals(idReceita)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Etapa não pertence à receita informada");
        }

        // Atualiza os campos
        etapaExistente.setOrdem(etapaReceitaRequest.getOrdem());
        etapaExistente.setConteudo(etapaReceitaRequest.getConteudo());

        // Salva e retorna a entidade atualizada
        return etapaReceitaRepository.save(etapaExistente);
    }

    @Transactional
    public EtapaReceitaResponse remover(Integer idEtapa) {
        EtapaReceita etapa = etapaReceitaRepository.findById(idEtapa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não encontrada"));

        etapaReceitaRepository.deleteById(idEtapa);

        return modelMapper.map(etapa, EtapaReceitaResponse.class);
    }
}
