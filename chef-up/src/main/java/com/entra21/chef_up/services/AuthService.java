package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Login.LoginRequest;
import com.entra21.chef_up.dtos.Login.LoginResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthService(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
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
