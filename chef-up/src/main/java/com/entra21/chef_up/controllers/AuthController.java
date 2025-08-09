package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.login.LoginRequest;
import com.entra21.chef_up.dtos.login.LoginResponse;
import com.entra21.chef_up.entities.Usuario;
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

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuariosRepository;
    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuariosRepository, UserDetailsService userDetailsService, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));
        return this.usuariosRepository.save(usuario);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        UserDetails user = userDetailsService.loadUserByUsername(request.email);

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new BadCredentialsException("Senha inválida");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}
