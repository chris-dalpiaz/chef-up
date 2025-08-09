package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.login.LoginRequest;
import com.entra21.chef_up.dtos.login.LoginResponse;
import com.entra21.chef_up.entities.ProgressoUsuario;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repository.ProgressoUsuarioRepository;
import com.entra21.chef_up.repository.UsuarioRepository;
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

    private final UsuarioRepository usuarioRepository;
    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ProgressoUsuarioRepository progressoUsuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository, UserDetailsService userDetailsService, JWTService jwtService, PasswordEncoder passwordEncoder, ProgressoUsuarioRepository progressoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.progressoUsuarioRepository = progressoUsuarioRepository;
    }

    // Cria um novo usuário
    @PostMapping("/register")
    public Usuario criarUsuario(@RequestBody Usuario usuario, ProgressoUsuario progressoUsuario) {
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));
        usuarioRepository.save(usuario);

        // Cria o progresso inicial do usuário
        progressoUsuario.setUsuario(usuario);
        progressoUsuario.setNivel(1);
        progressoUsuario.setXp(0);
        progressoUsuario.setAtualizadoEm(LocalDateTime.now());

        progressoUsuarioRepository.save(progressoUsuario);

        return usuario;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        UserDetails user = userDetailsService.loadUserByUsername(request.email);

        if (!passwordEncoder.matches(request.senha, user.getPassword())) {
            throw new BadCredentialsException("Senha inválida");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}
