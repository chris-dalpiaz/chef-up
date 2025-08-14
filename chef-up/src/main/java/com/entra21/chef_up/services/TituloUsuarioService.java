package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioResponse;
import com.entra21.chef_up.dtos.TituloUsuario.TituloUsuarioRequest;
import com.entra21.chef_up.dtos.TituloUsuario.TituloUsuarioResponse;
import com.entra21.chef_up.entities.*;
import com.entra21.chef_up.repositories.TituloRepository;
import com.entra21.chef_up.repositories.TituloUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TituloUsuarioService {

    private final TituloRepository tituloRepository;
    private final UsuarioRepository usuarioRepository;
    private final TituloUsuarioRepository tituloUsuarioRepository;
    private final ModelMapper modelMapper;

    public TituloUsuarioService(TituloRepository tituloRepository, UsuarioRepository usuarioRepository, TituloUsuarioRepository tituloUsuarioRepository, ModelMapper modelMapper) {
        this.tituloRepository = tituloRepository;
        this.usuarioRepository = usuarioRepository;
        this.tituloUsuarioRepository = tituloUsuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<TituloUsuarioResponse> listarTodos(Integer idUsuario) {
        return tituloUsuarioRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(etapa -> modelMapper.map(etapa, TituloUsuarioResponse.class))
                .toList();
    }

    public TituloUsuarioResponse buscar(Integer idUsuario, Integer idTituloUsuario) {
        TituloUsuario tituloUsuario = tituloUsuarioRepository.findById(idTituloUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!tituloUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pertence ao usuário");
        }

        return modelMapper.map(tituloUsuario, TituloUsuarioResponse.class);
    }

    public TituloUsuarioResponse criar(Integer idUsuario, TituloUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Titulo titulo = tituloRepository.findById(request.getIdTitulo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Título não encontrado"));

        TituloUsuario tituloUsuario = new TituloUsuario();
        tituloUsuario.setTitulo(titulo);
        tituloUsuario.setUsuario(usuario);
        tituloUsuario.setDesbloqueadoEm(LocalDateTime.now());

        TituloUsuario salvo = tituloUsuarioRepository.save(tituloUsuario);

        return modelMapper.map(salvo, TituloUsuarioResponse.class);
    }

    public TituloUsuarioResponse alterar(Integer idUsuario, Integer idTituloUsuario, TituloUsuarioRequest request) {
        TituloUsuario tituloUsuario = tituloUsuarioRepository.findById(idTituloUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!tituloUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pertence ao usuário");
        }

        // Atualiza os dados do adjetivo associado, se necessário
        if (request.getIdTitulo() != null && !request.getIdTitulo().equals(tituloUsuario.getTitulo().getId())) {
            Titulo novoTitulo = tituloRepository.findById(request.getIdTitulo())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo título não encontrado"));
            tituloUsuario.setTitulo(novoTitulo);
        }

        // Salva as alterações
        TituloUsuario atualizado = tituloUsuarioRepository.save(tituloUsuario);

        return modelMapper.map(atualizado, TituloUsuarioResponse.class);
    }


    @Transactional
    public TituloUsuarioResponse remover(Integer idUsuario, Integer idITituloUsuario) {
        /// Busca pelo ID ou lança 404
        TituloUsuario tituloUsuario = tituloUsuarioRepository.findById(idITituloUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário informado
        if (!tituloUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pertence ao usuário");
        }

        /// Deleta pelo ID
        tituloUsuarioRepository.deleteById(idITituloUsuario);

        /// Retorna o DTO do adjetivo deletado
        return modelMapper.map(tituloUsuario, TituloUsuarioResponse.class);
    }
}
