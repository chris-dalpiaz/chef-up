package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Avatar.AvatarRequest;
import com.entra21.chef_up.dtos.Avatar.AvatarResponse;
import com.entra21.chef_up.entities.Avatar;
import com.entra21.chef_up.repositories.AvatarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final ModelMapper modelMapper;

    public AvatarService(AvatarRepository avatarRepository, ModelMapper modelMapper) {
        this.avatarRepository = avatarRepository;
        this.modelMapper = modelMapper;
    }

    public List<AvatarResponse> listarTodos() {
        return avatarRepository.findAll().stream()
                .map(u -> modelMapper.map(u, AvatarResponse.class))
                .toList();
    }

    public AvatarResponse buscar(Integer id) {
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Avatar não encontrado"));

        return modelMapper.map(avatar, AvatarResponse.class);
    }

    public AvatarResponse criar(AvatarRequest request) {
        /// Converte o DTO de requisição para a entidade
        Avatar avatar = modelMapper.map(request, Avatar.class);

        /// Salva a entidade no banco de dados
        Avatar salvo = avatarRepository.save(avatar);

        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, AvatarResponse.class);
    }

    public AvatarResponse alterar(Integer id, AvatarRequest request) {
        /// Busca pelo ID ou lança erro 404
        Avatar alterar = avatarRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Avatar não encontrado"));

        /// Atualiza o nome com os dados do request
        alterar.setNome(request.getNome());
        alterar.setImagemUrl(request.getImagemUrl());

        /// Salva a alteração no banco
        Avatar salvo = avatarRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, AvatarResponse.class);
    }

    public AvatarResponse remover(Integer id) {
        /// Busca pelo ID ou lança 404
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Avatar não encontrado"));

        /// Deleta pelo ID
        avatarRepository.deleteById(id);

        /// Retorna o DTO do deletado
        return modelMapper.map(avatar, AvatarResponse.class);
    }
}
