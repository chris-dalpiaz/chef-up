package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioRequest;
import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioResponse;
import com.entra21.chef_up.entities.Adjetivo;
import com.entra21.chef_up.entities.AdjetivoUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.AdjetivoRepository;
import com.entra21.chef_up.repositories.AdjetivoUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdjetivoUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AdjetivoUsuarioRepository adjetivoUsuarioRepository;
    private final AdjetivoRepository adjetivoRepository;
    private final ModelMapper modelMapper;

    public AdjetivoUsuarioService(UsuarioRepository usuarioRepository, AdjetivoUsuarioRepository adjetivoUsuarioRepository, AdjetivoRepository adjetivoRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.adjetivoUsuarioRepository = adjetivoUsuarioRepository;
        this.adjetivoRepository = adjetivoRepository;
        this.modelMapper = modelMapper;
    }

    public List<AdjetivoUsuarioResponse> listarTodos() {
        return adjetivoUsuarioRepository.findAll().stream().map(au -> modelMapper.map(au, AdjetivoUsuarioResponse.class)).toList();
    }

    public AdjetivoUsuarioResponse buscar(Integer idUsuario, Integer idAdjetivoUsuario) {
        AdjetivoUsuario adjetivoUsuario = adjetivoUsuarioRepository.findById(idAdjetivoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!adjetivoUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        return modelMapper.map(adjetivoUsuario, AdjetivoUsuarioResponse.class);
    }

    public AdjetivoUsuarioResponse criar(Integer idUsuario, AdjetivoUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Adjetivo adjetivo = adjetivoRepository.findById(request.getIdAdjetivo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adjetivo não encontrado"));

        AdjetivoUsuario adjetivoUsuario = new AdjetivoUsuario();
        adjetivoUsuario.setAdjetivo(adjetivo);
        adjetivoUsuario.setUsuario(usuario);

        AdjetivoUsuario salvo = adjetivoUsuarioRepository.save(adjetivoUsuario);

        return modelMapper.map(salvo, AdjetivoUsuarioResponse.class);
    }

    public AdjetivoUsuarioResponse alterar(Integer idUsuario, Integer idAdjetivoUsuario, AdjetivoUsuarioRequest request) {
        AdjetivoUsuario adjetivoUsuario = adjetivoUsuarioRepository.findById(idAdjetivoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!adjetivoUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        // Atualiza os dados do adjetivo associado, se necessário
        if (request.getIdAdjetivo() != null && !request.getIdAdjetivo().equals(adjetivoUsuario.getAdjetivo().getId())) {
            Adjetivo novoAdjetivo = adjetivoRepository.findById(request.getIdAdjetivo())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo adjetivo não encontrado"));
            adjetivoUsuario.setAdjetivo(novoAdjetivo);
        }

        // Salva as alterações
        AdjetivoUsuario atualizado = adjetivoUsuarioRepository.save(adjetivoUsuario);

        return modelMapper.map(atualizado, AdjetivoUsuarioResponse.class);
    }


    @Transactional
    public AdjetivoUsuarioResponse remover(Integer idUsuario, Integer idAdjetivoUsuario) {
        /// Busca pelo ID ou lança 404
        AdjetivoUsuario adjetivoUsuario = adjetivoUsuarioRepository.findById(idAdjetivoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário informado
        if (!adjetivoUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adjetivo não pertence ao usuário");
        }

        /// Deleta pelo ID
        adjetivoUsuarioRepository.deleteById(idAdjetivoUsuario);

        /// Retorna o DTO do adjetivo deletado
        return modelMapper.map(adjetivoUsuario, AdjetivoUsuarioResponse.class);
    }
}
