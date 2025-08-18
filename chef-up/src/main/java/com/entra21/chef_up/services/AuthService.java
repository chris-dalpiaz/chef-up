package com.entra21.chef_up.services;

import com.entra21.chef_up.dtos.Login.LoginRequest;
import com.entra21.chef_up.dtos.Login.LoginResponse;
import com.entra21.chef_up.dtos.Usuario.UsuarioResponse;
import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela autenticação do usuário e geração de token JWT.
 */
@Service
public class AuthService {

    private static final String ERROR_INVALID_CREDENTIALS = "Credenciais inválidas";

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UsuarioRepository userRepository;
    private final ModelMapper modelMapper;
    private final UsuarioService userService;

    public AuthService(UserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder,
                       JWTService jwtService,
                       UsuarioRepository usuarioRepository,
                       ModelMapper modelMapper,
                       UsuarioService userService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    /**
     * Autentica um usuário com base nas credenciais fornecidas e retorna um token JWT.
     *
     * @param loginRequest DTO contendo email e senha
     * @return DTO contendo o token JWT
     */
    public LoginResponse authenticate(LoginRequest loginRequest) {
        UserDetails userDetails = loadUser(loginRequest.getEmail());
        validateCredentials(loginRequest.getSenha(), userDetails.getPassword());
        String jwtToken = generateToken(userDetails);

        // Obtenha o usuário completo e converta para DTO
        Usuario user = userService.findByEmail(loginRequest.getEmail());

        return buildResponse(jwtToken, modelMapper.map(user, UsuarioResponse.class));
    }

    /**
     * Carrega o usuário pelo username (email) ou lança exceção caso não exista.
     *
     * @param username email do usuário
     * @return detalhes do usuário
     */
    private UserDetails loadUser(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    /**
     * Verifica se a senha informada corresponde à senha armazenada.
     *
     * @param rawPassword     senha em texto plano
     * @param encodedPassword senha criptografada
     * @throws BadCredentialsException se as senhas não coincidirem
     */
    private void validateCredentials(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException(ERROR_INVALID_CREDENTIALS);
        }
    }

    /**
     * Gera o token JWT para o usuário autenticado.
     *
     * @param userDetails detalhes do usuário
     * @return token JWT
     */
    private String generateToken(UserDetails userDetails) {
        return jwtService.generateToken(userDetails);
    }

    /**
     * Constrói o DTO de resposta contendo o token JWT.
     *
     * @param jwtToken token gerado
     * @return DTO de resposta
     */
    private LoginResponse buildResponse(String jwtToken, UsuarioResponse user) {
        return new LoginResponse(jwtToken, user);
    }
}
