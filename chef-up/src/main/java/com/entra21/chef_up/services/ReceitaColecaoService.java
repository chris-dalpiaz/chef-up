package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioResponse;
import com.entra21.chef_up.dtos.ReceitaColecao.ReceitaColecaoRequest;
import com.entra21.chef_up.dtos.ReceitaColecao.ReceitaColecaoResponse;
import com.entra21.chef_up.entities.Colecao;
import com.entra21.chef_up.entities.Receita;
import com.entra21.chef_up.entities.ReceitaColecao;
import com.entra21.chef_up.repositories.ColecaoRepository;
import com.entra21.chef_up.repositories.ReceitaColecaoRepository;
import com.entra21.chef_up.repositories.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReceitaColecaoService {

    private final ReceitaColecaoRepository receitaColecaoRepository;
    private final ModelMapper modelMapper;
    private final ColecaoRepository colecaoRepository;
    private final ReceitaRepository receitaRepository;

    public ReceitaColecaoService(ReceitaColecaoRepository receitaColecaoRepository, ModelMapper modelMapper, ColecaoService colecaoService, ColecaoRepository colecaoRepository, ReceitaService receitaService, ReceitaRepository receitaRepository) {
        this.receitaColecaoRepository = receitaColecaoRepository;
        this.modelMapper = modelMapper;
        this.colecaoRepository = colecaoRepository;
        this.receitaRepository = receitaRepository;
    }

    public List<ReceitaColecaoResponse> listarTodos(Integer idColecao) {
        return receitaColecaoRepository.findByColecaoId(idColecao)
                .stream()
                .map(etapa -> modelMapper.map(etapa, ReceitaColecaoResponse.class))
                .toList();
    }

    public ReceitaColecaoResponse buscar(Integer idColecao, Integer idReceitaColecao) {
        ReceitaColecao receitaColecao = receitaColecaoRepository.findById(idReceitaColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence à coleção informada
        if (!receitaColecao.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }

        return modelMapper.map(receitaColecao, ReceitaColecaoResponse.class);
    }

    public ReceitaColecaoResponse criar(Integer idColecao, ReceitaColecaoRequest request) {
        Colecao colecao = colecaoRepository.findById(idColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleção não encontrada"));

        Receita receita = receitaRepository.findById(request.getIdReceita())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada"));

        ReceitaColecao receitaColecao = new ReceitaColecao();
        receitaColecao.setColecao(colecao);
        receitaColecao.setReceita(receita);

        ReceitaColecao salvo = receitaColecaoRepository.save(receitaColecao);

        return modelMapper.map(salvo, ReceitaColecaoResponse.class);
    }

    public ReceitaColecaoResponse alterar(Integer idColecao, Integer idReceitaColecao, ReceitaColecaoRequest request) {
        ReceitaColecao receitaColecao = receitaColecaoRepository.findById(idReceitaColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence à coleção informada
        if (!receitaColecao.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }

        // Atualiza os dados da receita associada, se necessário
        if (request.getIdReceita() != null && !request.getIdReceita().equals(receitaColecao.getReceita().getId())) {
            Receita novaReceita = receitaRepository.findById(request.getIdReceita())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nova receita não encontrada"));
            receitaColecao.setReceita(novaReceita);
        }

        // Salva as alterações
        ReceitaColecao atualizado = receitaColecaoRepository.save(receitaColecao);

        return modelMapper.map(atualizado, ReceitaColecaoResponse.class);
    }


    @Transactional
    public ReceitaColecaoResponse remover(Integer idColecao, Integer idReceitaColecao) {
        /// Busca pelo ID ou lança 404
        ReceitaColecao receitaColecao = receitaColecaoRepository.findById(idReceitaColecao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence à coleção informada
        if (!receitaColecao.getColecao().getId().equals(idColecao)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence à coleção informada");
        }

        /// Deleta pelo ID
        receitaColecaoRepository.deleteById(idReceitaColecao);

        /// Retorna o DTO do adjetivo deletado
        return modelMapper.map(receitaColecao, ReceitaColecaoResponse.class);
    }
}
