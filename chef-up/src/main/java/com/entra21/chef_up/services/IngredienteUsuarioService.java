package com.entra21.chef_up.services;


import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioResponse;
import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioRequest;
import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioResponse;
import com.entra21.chef_up.entities.Ingrediente;
import com.entra21.chef_up.entities.IngredienteUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.IngredienteRepository;
import com.entra21.chef_up.repositories.IngredienteUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IngredienteUsuarioService {

    private final IngredienteRepository ingredienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final IngredienteUsuarioRepository ingredienteUsuarioRepository;
    private final ModelMapper modelMapper;

    public IngredienteUsuarioService(IngredienteRepository ingredienteRepository, UsuarioRepository usuarioRepository, IngredienteUsuarioRepository ingredienteUsuarioRepository, ModelMapper modelMapper) {
        this.ingredienteRepository = ingredienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.ingredienteUsuarioRepository = ingredienteUsuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<IngredienteUsuarioResponse> listarTodos(Integer idUsuario) {
        return ingredienteUsuarioRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(etapa -> modelMapper.map(etapa, IngredienteUsuarioResponse.class))
                .toList();
    }

    public IngredienteUsuarioResponse buscar(Integer idUsuario, Integer idIngredienteUsuario) {
        IngredienteUsuario ingredienteUsuario = ingredienteUsuarioRepository.findById(idIngredienteUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!ingredienteUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        return modelMapper.map(ingredienteUsuario, IngredienteUsuarioResponse.class);
    }

    public IngredienteUsuarioResponse criar(Integer idUsuario, IngredienteUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Ingrediente ingrediente = ingredienteRepository.findById(request.getIdIngrediente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        IngredienteUsuario ingredienteUsuario = new IngredienteUsuario();
        ingredienteUsuario.setIngrediente(ingrediente);
        ingredienteUsuario.setUsuario(usuario);
        ingredienteUsuario.setDataAdicionada(LocalDateTime.now());

        IngredienteUsuario salvo = ingredienteUsuarioRepository.save(ingredienteUsuario);

        return modelMapper.map(salvo, IngredienteUsuarioResponse.class);
    }

    public IngredienteUsuarioResponse alterar(Integer idUsuario, Integer idIngredienteUsuario, IngredienteUsuarioRequest request) {
        IngredienteUsuario ingredienteUsuario = ingredienteUsuarioRepository.findById(idIngredienteUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!ingredienteUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        // Atualiza os dados do adjetivo associado, se necessário
        if (request.getIdIngrediente() != null && !request.getIdIngrediente().equals(ingredienteUsuario.getIngrediente().getId())) {
            Ingrediente novoIngrediente = ingredienteRepository.findById(request.getIdIngrediente())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo ingrediente não encontrado"));
            ingredienteUsuario.setIngrediente(novoIngrediente);
        }

        // Salva as alterações
        IngredienteUsuario atualizado = ingredienteUsuarioRepository.save(ingredienteUsuario);

        return modelMapper.map(atualizado, IngredienteUsuarioResponse.class);
    }


    @Transactional
    public IngredienteUsuarioResponse remover(Integer idUsuario, Integer idIngredienteUsuario) {
        /// Busca pelo ID ou lança 404
        IngredienteUsuario ingredienteUsuario = ingredienteUsuarioRepository.findById(idIngredienteUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário informado
        if (!ingredienteUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediente não pertence ao usuário");
        }

        /// Deleta pelo ID
        ingredienteUsuarioRepository.deleteById(idIngredienteUsuario);

        /// Retorna o DTO do adjetivo deletado
        return modelMapper.map(ingredienteUsuario, IngredienteUsuarioResponse.class);
    }
}
