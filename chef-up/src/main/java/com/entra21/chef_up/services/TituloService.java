package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Titulo.TituloRequest;
import com.entra21.chef_up.dtos.Titulo.TituloResponse;
import com.entra21.chef_up.entities.Titulo;
import com.entra21.chef_up.repositories.TituloRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TituloService {
    private final TituloRepository tituloRepository;
    private final ModelMapper modelMapper;

    public TituloService(TituloRepository tituloRepository,
                         ModelMapper modelMapper) {
        this.tituloRepository = tituloRepository;
        this.modelMapper = modelMapper;
    }

    public List<TituloResponse> listarTodos() {
        return tituloRepository.findAll().stream()
                .map(u -> modelMapper.map(u, TituloResponse.class))
                .toList();
    }

    public TituloResponse buscar(Integer id) {
        Titulo titulo = tituloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Título não encontrado"));

        return modelMapper.map(titulo, TituloResponse.class);
    }

    public TituloResponse criar(TituloRequest request) {
        /// Converte o DTO de requisição para a entidade Título
        Titulo titulo = modelMapper.map(request, Titulo.class);

        /// Salva a entidade no banco de dados
        Titulo salvo = tituloRepository.save(titulo);

        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, TituloResponse.class);
    }

    public TituloResponse alterar(Integer id, TituloRequest request) {
        /// Busca o título pelo ID ou lança erro 404
        Titulo alterar = tituloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Título não encontrado"));

        /// Atualiza o nome com os dados do request
        alterar.setNome(request.getNome());
        alterar.setCondicaoDesbloqueio(request.getCondicaoDesbloqueio());

        /// Salva a alteração no banco
        Titulo salvo = tituloRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, TituloResponse.class);
    }

    public TituloResponse remover(Integer id) {
        /// Busca o título pelo ID ou lança 404
        Titulo titulo = tituloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Título não encontrado"));

        /// Deleta o título pelo ID
        tituloRepository.deleteById(id);

        /// Retorna o DTO do título deletado
        return modelMapper.map(titulo, TituloResponse.class);
    }
}
