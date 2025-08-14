package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioResponse;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioRequest;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioResponse;
import com.entra21.chef_up.entities.*;
import com.entra21.chef_up.repositories.ReceitaRepository;
import com.entra21.chef_up.repositories.ReceitaUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReceitaUsuarioService {

    private final ReceitaUsuarioRepository receitaUsuarioRepository;
    private final ReceitaRepository receitaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public ReceitaUsuarioService(ReceitaUsuarioRepository receitaUsuarioRepository, ReceitaRepository receitaRepository, UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.receitaUsuarioRepository = receitaUsuarioRepository;
        this.receitaRepository = receitaRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<ReceitaUsuarioResponse> listarTodos(Integer idUsuario) {
        return receitaUsuarioRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(etapa -> modelMapper.map(etapa, ReceitaUsuarioResponse.class))
                .toList();
    }

    public ReceitaUsuarioResponse buscar(Integer idUsuario, Integer idReceitaUsuario) {
        ReceitaUsuario receitaUsuario = receitaUsuarioRepository.findById(idReceitaUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!receitaUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence ao usuário");
        }

        return modelMapper.map(receitaUsuario, ReceitaUsuarioResponse.class);
    }

    public ReceitaUsuarioResponse criar(Integer idUsuario, ReceitaUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Receita receita = receitaRepository.findById(request.getIdReceita())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrado"));

        ReceitaUsuario receitaUsuario = new ReceitaUsuario();
        receitaUsuario.setReceita(receita);
        receitaUsuario.setUsuario(usuario);;
        receitaUsuario.setDataConclusao(LocalDateTime.now());
        receitaUsuario.setFotoPrato(request.getFotoPrato());

        ReceitaUsuario salvo = receitaUsuarioRepository.save(receitaUsuario);

        return modelMapper.map(salvo, ReceitaUsuarioResponse.class);
    }

    public ReceitaUsuarioResponse alterar(Integer idUsuario, Integer idReceitaUsuario, ReceitaUsuarioRequest request) {
        ReceitaUsuario receitaUsuario = receitaUsuarioRepository.findById(idReceitaUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!receitaUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence ao usuário");
        }

        // Atualiza os dados do adjetivo associado, se necessário
        if (request.getIdReceita() != null && !request.getIdReceita().equals(receitaUsuario.getReceita().getId())) {
            Receita novaReceita = receitaRepository.findById(request.getIdReceita())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nova Receita não encontrada"));
            receitaUsuario.setReceita(novaReceita);
        }

        // Salva as alterações
        ReceitaUsuario atualizado = receitaUsuarioRepository.save(receitaUsuario);

        return modelMapper.map(atualizado, ReceitaUsuarioResponse.class);
    }


    @Transactional
    public ReceitaUsuarioResponse remover(Integer idUsuario, Integer idReceitaUsuario) {
        /// Busca pelo ID ou lança 404
        ReceitaUsuario receitaUsuario = receitaUsuarioRepository.findById(idReceitaUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário informado
        if (!receitaUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receita não pertence ao usuário");
        }

        /// Deleta pelo ID
        receitaUsuarioRepository.deleteById(idReceitaUsuario);

        /// Retorna o DTO do adjetivo deletado
        return modelMapper.map(receitaUsuario, ReceitaUsuarioResponse.class);
    }
}
