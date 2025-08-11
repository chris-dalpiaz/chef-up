package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Ingrediente.IngredienteRequest;
import com.entra21.chef_up.dtos.Ingrediente.IngredienteResponse;
import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.repositories.IngredienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngredienteService {
    /** Repositório para acesso aos dados dos ingredientes */
    private final IngredienteRepository ingredienteRepository;
    private final ModelMapper modelMapper;

    /**
     * Construtor com injeção de dependência.
     * Recebe o repositório para manipular ingredientes.
     */
    public IngredienteService(IngredienteRepository ingredienteRepository,
                              ModelMapper modelMapper) {
        this.ingredienteRepository = ingredienteRepository;
        this.modelMapper = modelMapper;
    }

    public List<IngredienteResponse> listarTodos() {
        return ingredienteRepository.findAll().stream()
                .map(u -> modelMapper.map(u, IngredienteResponse.class))
                .toList();
    }

    public IngredienteResponse buscar(Integer id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ingrediente não encontrado"));

        return modelMapper.map(ingrediente, IngredienteResponse.class);
    }

    public IngredienteResponse criar(IngredienteRequest request) {
        /// Converte o DTO de requisição para a entidade
        Ingrediente ingrediente = modelMapper.map(request, Ingrediente.class);

        /// Salva a entidade no banco de dados
        Ingrediente salvo = ingredienteRepository.save(ingrediente);

        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, IngredienteResponse.class);
    }

    public IngredienteResponse alterar(Integer id, IngredienteRequest request) {
        /// Busca pelo ID ou lança erro 404
        Ingrediente alterar = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ingrediente não encontrado"));

        /// Atualiza o nome com os dados do request
        alterar.setCategoria(request.getCategoria());
        alterar.setNome(request.getNome());
        alterar.setDicaConservacao(request.getDicaConservacao());
        alterar.setEstimativaValidade(request.getEstimativaValidade());

        /// Salva a alteração no banco
        Ingrediente salvo = ingredienteRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, IngredienteResponse.class);
    }

    public IngredienteResponse remover(Integer id) {
        /// Busca pelo ID ou lança 404
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ingrediente não encontrado"));

        /// Deleta pelo ID
        ingredienteRepository.deleteById(id);

        /// Retorna o DTO do deletado
        return modelMapper.map(ingrediente, IngredienteResponse.class);
    }
}
