package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioResponse;
import com.entra21.chef_up.dtos.AvatarUsuario.AvatarUsuarioRequest;
import com.entra21.chef_up.dtos.AvatarUsuario.AvatarUsuarioResponse;
import com.entra21.chef_up.entities.Avatar;
import com.entra21.chef_up.entities.AvatarUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.AvatarRepository;
import com.entra21.chef_up.repositories.AvatarUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AvatarUsuarioService {

    private final AvatarUsuarioRepository avatarUsuarioRepository;
    private final AvatarRepository avatarRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public AvatarUsuarioService(AvatarUsuarioRepository avatarUsuarioRepository, AvatarRepository avatarRepository, UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.avatarUsuarioRepository = avatarUsuarioRepository;
        this.avatarRepository = avatarRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<AvatarUsuarioResponse> listarTodos(Integer idUsuario) {
            return avatarUsuarioRepository.findByUsuarioId(idUsuario)
                    .stream()
                    .map(etapa -> modelMapper.map(etapa, AvatarUsuarioResponse.class))
                    .toList();
        }

    public AvatarUsuarioResponse buscar(Integer idUsuario, Integer idAvatarUsuario) {
        AvatarUsuario avatarUsuario = avatarUsuarioRepository.findById(idAvatarUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!avatarUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        return modelMapper.map(avatarUsuario, AvatarUsuarioResponse.class);
    }

    public AvatarUsuarioResponse criar(Integer idUsuario, AvatarUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Avatar avatar = avatarRepository.findById(request.getIdAvatar())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        AvatarUsuario avatarUsuario = new AvatarUsuario();
        avatarUsuario.setAvatar(avatar);
        avatarUsuario.setUsuario(usuario);

        AvatarUsuario salvo = avatarUsuarioRepository.save(avatarUsuario);

        return modelMapper.map(salvo, AvatarUsuarioResponse.class);
    }

    public AvatarUsuarioResponse alterar(Integer idUsuario, Integer idAvatarUsuario, AvatarUsuarioRequest request) {
        AvatarUsuario avatarUsuario = avatarUsuarioRepository.findById(idAvatarUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário
        if (!avatarUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        // Atualiza os dados do adjetivo associado, se necessário
        if (request.getIdAvatar() != null && !request.getIdAvatar().equals(avatarUsuario.getAvatar().getId())) {
            Avatar novoAvatar = avatarRepository.findById(request.getIdAvatar())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novo avatar não encontrado"));
            avatarUsuario.setAvatar(novoAvatar);
        }

        // Salva as alterações
        AvatarUsuario atualizado = avatarUsuarioRepository.save(avatarUsuario);

        return modelMapper.map(atualizado, AvatarUsuarioResponse.class);
    }


    @Transactional
    public AvatarUsuarioResponse remover(Integer idUsuario, Integer idAvatarUsuario) {
        /// Busca pelo ID ou lança 404
        AvatarUsuario avatarUsuario = avatarUsuarioRepository.findById(idAvatarUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada"));

        // Verifica se a associação pertence ao usuário informado
        if (!avatarUsuario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avatar não pertence ao usuário");
        }

        /// Deleta pelo ID
        avatarUsuarioRepository.deleteById(idAvatarUsuario);

        /// Retorna o DTO do adjetivo deletado
        return modelMapper.map(avatarUsuario, AvatarUsuarioResponse.class);
    }
}
