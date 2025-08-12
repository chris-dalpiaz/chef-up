package com.entra21.chef_up.services;


import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.entities.ProgressoUsuario;
import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.ProgressoUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final PronomeService pronomeService;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, ProgressoUsuarioRepository progressoUsuarioRepository, PronomeService pronomeService) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.pronomeService = pronomeService;
    }

    /// Busca todos usuários do banco e cria um stream para processar um a um
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream().map(u -> {
            UsuarioResponse usuarioResponse = modelMapper.map(u, UsuarioResponse.class);

            /// Mapear pronome manualmente, pois o modelmapper não consegue listar objetos complexos
            usuarioResponse.setPronome(pronomeService.mapParaResponse(u.getPronome()));

            return usuarioResponse;
        }).toList();
    }


    public UsuarioResponse buscar(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    public UsuarioResponse criar(UsuarioRequest request) {

        // Mapeia o request para a entidade Usuario
        Usuario usuario = modelMapper.map(request, Usuario.class);
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));
        usuario.setId(null);

        // Cria o progresso do usuário
        ProgressoUsuario progresso = new ProgressoUsuario();
        progresso.setNivel(1);
        progresso.setXp(0);
        progresso.setAtualizadoEm(LocalDateTime.now());

        // Estabelece a relação bidirecional
        usuario.setProgressoUsuario(progresso);
        progresso.setUsuario(usuario);

        // Salva o usuário e o progresso em cascata
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Retorna o DTO
        return modelMapper.map(usuarioSalvo, UsuarioResponse.class);
    }


    public UsuarioResponse alterar(Integer id, UsuarioRequest request) {
        /// Busca pelo ID ou lança erro 404
        Usuario alterar = usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        /// Atualiza o nome com os dados do request
        alterar.setNome(request.getNome());

        /// Buscar e atualizar pronome
        Pronome pronome = pronomeService.buscarPorId(request.getIdPronome());
        alterar.setPronome(pronome);

        /// Salva a alteração no banco
        Usuario salvo = usuarioRepository.save(alterar);

        /// Converte a entidade salva para DTO de resposta e retorna
        return modelMapper.map(salvo, UsuarioResponse.class);
    }

    public UsuarioResponse remover(Integer id) {
        /// Busca pelo ID ou lança 404
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        /// Deleta pelo ID
        usuarioRepository.deleteById(id);

        /// Retorna o DTO do deletado
        return modelMapper.map(usuario, UsuarioResponse.class);
    }
}
