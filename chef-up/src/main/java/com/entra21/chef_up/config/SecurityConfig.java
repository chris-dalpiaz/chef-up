package com.entra21.chef_up.config;

import com.entra21.chef_up.filters.JwtAuthFilter;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração de segurança da aplicação, usando Spring Security.
 * Define regras de acesso, filtros e codificação de senhas.
 */
@Configuration
@EnableWebSecurity  /// Ativa a configuração de segurança web do Spring Security
public class SecurityConfig {

    /**
     * Filtro customizado que valida e autentica requisições usando JWT (JSON Web Token).
     * Esse filtro intercepta as requisições e verifica o token para autenticação.
     */
    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Injeção do filtro JWT via construtor.
     * Isso permite que o filtro seja usado na configuração da cadeia de segurança HTTP.
     */
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Define a cadeia de filtros que será aplicada às requisições HTTP.
     * Aqui se configura as regras de autorização e quais filtros serão usados.
     *
     * @param http Objeto para customizar a segurança HTTP (endpoints, autenticação, filtros)
     * @return a cadeia de filtros de segurança configurada
     * @throws Exception se ocorrer erro durante configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                /// Desativa proteção contra CSRF (Cross-Site Request Forgery),
                /// pois a aplicação usa tokens JWT e não sessão/cookies.
                .csrf().disable()

                /// Configura autorização das requisições:
                .authorizeHttpRequests()

                /// Permite acesso livre (sem autenticação) para todos endpoints que
                /// começam com "/auth/", ou seja, rotas de login e registro.
                .requestMatchers("/auth/*").permitAll()

                /// Para todas as outras rotas, exige autenticação.
                .anyRequest().authenticated()

                .and()

                /// Adiciona o filtro jwtAuthFilter antes do filtro padrão de autenticação
                /// (UsernamePasswordAuthenticationFilter), para interceptar as requisições
                /// e validar o token JWT antes do Spring tentar autenticar.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        /// Retorna a configuração finalizada
        return http.build();
    }

    /**
     * Expõe o AuthenticationManager como bean para ser usado em outras partes da aplicação.
     * O AuthenticationManager é responsável por processar a autenticação do usuário.
     *
     * @param config configurações de autenticação padrão do Spring Security
     * @return AuthenticationManager configurado
     * @throws Exception caso ocorra erro na criação
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Obtém o AuthenticationManager padrão do Spring para autenticar usuários
        return config.getAuthenticationManager();
    }

    /**
     * Bean para codificação de senhas usando algoritmo BCrypt.
     * BCrypt é considerado seguro para hashing de senhas, pois é lento e resistente a ataques de força bruta.
     *
     * @return PasswordEncoder que usa BCrypt para hash de senhas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /// Cria instância do codificador BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }
}
