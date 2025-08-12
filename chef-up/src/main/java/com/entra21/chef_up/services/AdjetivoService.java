package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Adjetivo.AdjetivoRequest;
import com.entra21.chef_up.dtos.Adjetivo.AdjetivoResponse;
import com.entra21.chef_up.entities.Adjetivo;
import com.entra21.chef_up.repositories.AdjetivoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdjetivoService {

    private final AdjetivoRepository adjetivoRepository;
    private final ModelMapper modelMapper;

    public AdjetivoService(AdjetivoRepository adjetivoRepository, ModelMapper modelMapper) {
        this.adjetivoRepository = adjetivoRepository;
        this.modelMapper = modelMapper;
    }

    public List<AdjetivoResponse> listarTodos() {
        return adjetivoRepository.findAll().stream().map(u -> modelMapper.map(u, AdjetivoResponse.class)).toList();
    }

    public AdjetivoResponse buscar(Integer id) {
        Adjetivo adjetivo = adjetivoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        return modelMapper.map(adjetivo, AdjetivoResponse.class);
    }

    public AdjetivoResponse criar(AdjetivoRequest request) {
        /// Converte o DTO de requisição para a entidade Adjetivo
        Adjetivo adjetivo = modelMapper.map(request, Adjetivo.class);

        /// Salva a entidade no banco de dados
        Adjetivo salvo = adjetivoRepository.save(adjetivo);

        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, AdjetivoResponse.class);
    }

    public AdjetivoResponse alterar(Integer id, AdjetivoRequest request) {
        /// Busca o adjetivo pelo ID ou lança erro 404
        Adjetivo alterar = adjetivoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        /// Atualiza o nome com os dados do request
        alterar.setNome(request.getNome());

        /// Salva a alteração no banco
        Adjetivo salvo = adjetivoRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, AdjetivoResponse.class);
    }

    public AdjetivoResponse remover(Integer id) {
        /// Busca o adjetivo pelo ID ou lança 404
        Adjetivo adjetivo = adjetivoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        /// Deleta o adjetivo pelo ID
        adjetivoRepository.deleteById(id);

        /// Retorna o DTO do adjetivo deletado
        return modelMapper.map(adjetivo, AdjetivoResponse.class);
    }
}
