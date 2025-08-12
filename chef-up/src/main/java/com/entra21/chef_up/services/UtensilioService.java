package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Utensilio.UtensilioRequest;
import com.entra21.chef_up.dtos.Utensilio.UtensilioResponse;
import com.entra21.chef_up.entities.Utensilio;
import com.entra21.chef_up.repositories.UtensilioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UtensilioService {
    private final UtensilioRepository utensilioRepository;
    private final ModelMapper modelMapper;

    public UtensilioService(UtensilioRepository utensilioRepository, ModelMapper modelMapper) {
        this.utensilioRepository = utensilioRepository;
        this.modelMapper = modelMapper;
    }

    public List<UtensilioResponse> listarTodos() {
        return utensilioRepository.findAll().stream().map(u -> modelMapper.map(u, UtensilioResponse.class)).toList();
    }

    public UtensilioResponse buscar(Integer id) {
        Utensilio utensilio = utensilioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        return modelMapper.map(utensilio, UtensilioResponse.class);
    }

    public UtensilioResponse criar(UtensilioRequest request) {
        /// Converte o DTO de requisição para a entidade
        Utensilio utensilio = modelMapper.map(request, Utensilio.class);

        /// Salva a entidade no banco de dados
        Utensilio salvo = utensilioRepository.save(utensilio);

        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, UtensilioResponse.class);
    }

    public UtensilioResponse alterar(Integer id, UtensilioRequest request) {
        /// Busca pelo ID ou lança erro 404
        Utensilio alterar = utensilioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        /// Atualiza o nome com os dados do request
        alterar.setNome(request.getNome());

        /// Salva a alteração no banco
        Utensilio salvo = utensilioRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, UtensilioResponse.class);
    }

    public UtensilioResponse remover(Integer id) {
        /// Busca pelo ID ou lança 404
        Utensilio utensilio = utensilioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utensílio não encontrado"));

        /// Deleta pelo ID
        utensilioRepository.deleteById(id);

        /// Retorna o DTO do deletado
        return modelMapper.map(utensilio, UtensilioResponse.class);
    }
}
