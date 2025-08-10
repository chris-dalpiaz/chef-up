package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.login.LoginRequest;
import com.entra21.chef_up.dtos.login.LoginResponse;
import com.entra21.chef_up.entities.ProgressoUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.ProgressoUsuarioRepository;
import com.entra21.chef_up.repositories.UsuarioRepository;
import com.entra21.chef_up.services.JWTService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /** Repositório para CRUD de usuários */
    private final UsuarioRepository usuarioRepository;

    /** Serviço para carregar detalhes do usuário (usado no login) */
    private final UserDetailsService userDetailsService;

    /** Serviço para gerar token JWT */
    private final JWTService jwtService;

    /** Encoder para criptografar senhas */
    private final PasswordEncoder passwordEncoder;

    /** Repositório para CRUD do progresso do usuário */
    private final ProgressoUsuarioRepository progressoUsuarioRepository;

    /**
     * Construtor com injeção das dependências necessárias
     */
    public AuthController(UsuarioRepository usuarioRepository,
                          UserDetailsService userDetailsService,
                          JWTService jwtService,
                          PasswordEncoder passwordEncoder,
                          ProgressoUsuarioRepository progressoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.progressoUsuarioRepository = progressoUsuarioRepository;
    }

    /**
     * Registra um novo usuário.
     * Recebe o objeto Usuario no corpo da requisição (JSON).
     *
     * @param usuario dados do novo usuário (senha em texto simples)
     * @param progressoUsuario objeto para criar progresso inicial (recebido junto, mas ideal seria criado internamente)
     * @return usuário salvo no banco com ID gerado
     */
    @PostMapping("/register")
    public Usuario criarUsuario(@RequestBody Usuario usuario, ProgressoUsuario progressoUsuario) {
        /// Define a data de cadastro no momento atual
        usuario.setDataCadastro(LocalDateTime.now());

        /// Criptografa a senha recebida para salvar no banco
        usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));

        /// Salva o usuário no banco
        usuarioRepository.save(usuario);

        /// Inicializa o progresso do usuário com nível 1 e XP 0
        progressoUsuario.setUsuario(usuario);
        progressoUsuario.setNivel(1);
        progressoUsuario.setXp(0);
        progressoUsuario.setAtualizadoEm(LocalDateTime.now());

        /// Salva o progresso no banco
        progressoUsuarioRepository.save(progressoUsuario);

        /// Retorna o usuário criado
        return usuario;
    }

    /**
     * Realiza o login do usuário.
     * Recebe email e senha no corpo da requisição.
     *
     * @param request contém email e senha enviados pelo cliente
     * @return objeto com token JWT se login for bem sucedido
     * @throws BadCredentialsException se a senha estiver incorreta
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        /// Busca o usuário pelo email (username)
        UserDetails user = userDetailsService.loadUserByUsername(request.email);

        /// Verifica se a senha enviada bate com a senha criptografada no banco
        if (!passwordEncoder.matches(request.senha, user.getPassword())) {
            throw new BadCredentialsException("Senha inválida");
        }

        /// Gera token JWT para o usuário autenticado
        String token = jwtService.generateToken(user);

        /// Retorna o token em resposta
        return new LoginResponse(token);
    }
}
