package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Login.LoginRequest;
import com.entra21.chef_up.dtos.Login.LoginResponse;
import com.entra21.chef_up.dtos.Usuario.UsuarioRequest;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.services.AuthService;
import com.entra21.chef_up.services.UsuarioService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthService authService;

    /**
     * Construtor com injeção das dependências necessárias
     */
    public AuthController(UsuarioService usuarioService,
                          AuthService authService) {
        this.usuarioService = usuarioService;
        this.authService = authService;
    }

    /**
     * Registra um novo usuário.
     * Recebe o objeto Usuario no corpo da requisição (JSON).
     *
     * @param request dados do novo usuário (senha em texto simples)
     * @return usuário salvo no banco com ID gerado
     */
    @PostMapping("/register")
    public UsuarioResponse criarUsuario(@RequestBody UsuarioRequest request) {
        return usuarioService.criar(request);
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
        return authService.login(request);
    }
}
