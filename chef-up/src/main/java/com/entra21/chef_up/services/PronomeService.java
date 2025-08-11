package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Pronome.PronomeRequest;
import com.entra21.chef_up.dtos.Pronome.PronomeResponse;
import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.repositories.PronomeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PronomeService {
    private final PronomeRepository pronomeRepository;
    private final ModelMapper modelMapper;

    public PronomeService(PronomeRepository pronomeRepository,
                          ModelMapper modelMapper) {
        this.pronomeRepository = pronomeRepository;
        this.modelMapper = modelMapper;
    }

    public List<PronomeResponse> listarTodos() {
        return  pronomeRepository.findAll().stream()
                .map(u -> modelMapper.map(u, PronomeResponse.class))
                .toList();
    }

    public PronomeResponse buscar(Integer id) {
        Pronome pronome = pronomeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pronome não encontrado"));

        return modelMapper.map(pronome, PronomeResponse.class);
    }

    public PronomeResponse criar(PronomeRequest request) {
        /// Converte o DTO de requisição para a entidade
        Pronome pronome = modelMapper.map(request, Pronome.class);

        /// Salva a entidade no banco de dados
        Pronome salvo = pronomeRepository.save(pronome);

        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, PronomeResponse.class);
    }

    public PronomeResponse alterar(Integer id, PronomeRequest request) {
        /// Busca pelo ID ou lança erro 404
        Pronome alterar = pronomeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pronome não encontrado"));

        /// Atualiza o nome com os dados do request
        alterar.setNome(request.getNome());

        /// Salva a alteração no banco
        Pronome salvo = pronomeRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, PronomeResponse.class);
    }

    public PronomeResponse remover(Integer id) {
        /// Busca pelo ID ou lança 404
        Pronome pronome = pronomeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pronome não encontrado"));

        /// Deleta pelo ID
        pronomeRepository.deleteById(id);

        /// Retorna o DTO do deletado
        return modelMapper.map(pronome, PronomeResponse.class);
    }

    public Pronome buscarPorId(Integer idPronome) {
        return pronomeRepository.findById(idPronome).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Pronome não encontrado"));
    }

    public PronomeResponse mapParaResponse(Pronome pronome){
        return modelMapper.map(pronome, PronomeResponse.class);
    }
}
