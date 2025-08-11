package com.entra21.chef_up.services;


import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioRequest;
import com.entra21.chef_up.dtos.Pronome.PronomeResponse;
import com.entra21.chef_up.dtos.Usuario.UsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.entities.ProgressoUsuario;
import com.entra21.chef_up.entities.Pronome;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.ProgressoUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
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
    private final ProgressoUsuarioRepository progressoUsuarioRepository;
    private final PronomeService pronomeService;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          ModelMapper modelMapper,
                          PasswordEncoder passwordEncoder,
                          ProgressoUsuarioRepository progressoUsuarioRepository,
                          PronomeService pronomeService) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.progressoUsuarioRepository = progressoUsuarioRepository;
        this.pronomeService = pronomeService;
    }

    /// Busca todos usuários do banco e cria um stream para processar um a um
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(u -> {
                    UsuarioResponse usuarioResponse = modelMapper.map(u, UsuarioResponse.class);

                    /// Mapear pronome manualmente, pois o modelmapper não consegue listar objetos complexos
                    usuarioResponse.setPronome(pronomeService.mapParaResponse(u.getPronome()));

                    return usuarioResponse;
                })
                .toList();
    }


    public UsuarioResponse buscar(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"));

        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    public UsuarioResponse criar(UsuarioRequest request,
                                 ProgressoUsuarioRequest progressoUsuarioRequest) {

        /// Converte o DTO de requisição para a entidade
        Usuario usuario = modelMapper.map(request, Usuario.class);
        ProgressoUsuario progressoUsuario = modelMapper.map(progressoUsuarioRequest, ProgressoUsuario.class);

        /// Define a data de cadastro no momento atual
        usuario.setDataCadastro(LocalDateTime.now());

        /// Criptografa a senha recebida para salvar no banco
        usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));

        /// Inicializa o progresso do usuário com nível 1 e XP 0
        progressoUsuario.setUsuario(usuario);
        progressoUsuario.setNivel(1);
        progressoUsuario.setXp(0);
        progressoUsuario.setAtualizadoEm(LocalDateTime.now());

        /// Salva o progresso no banco
        progressoUsuarioRepository.save(progressoUsuario);
        /// Salva a entidade no banco de dados
        Usuario salvo = usuarioRepository.save(usuario);


        /// Converte a entidade salva para o DTO de resposta e retorna
        return modelMapper.map(salvo, UsuarioResponse.class);

    }

    public UsuarioResponse alterar(Integer id, UsuarioRequest request) {
        /// Busca pelo ID ou lança erro 404
        Usuario alterar = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"));

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
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"));

        /// Deleta pelo ID
        usuarioRepository.deleteById(id);

        /// Retorna o DTO do deletado
        return modelMapper.map(usuario, UsuarioResponse.class);
    }
}
