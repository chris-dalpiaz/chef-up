package com.entra21.chef_up.config;

import com.entra21.chef_up.filters.JwtAuthFilter;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
@Configuration // Indica que esta classe contém beans de configuração
@EnableWebSecurity // Ativa as configurações de segurança web do Spring Security
public class SecurityConfig {

    // Filtro personalizado para autenticação via JWT
    private final JwtAuthFilter jwtAuthFilter;

    // Injeta o filtro JWT via construtor
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Define a cadeia de segurança HTTP da aplicação.
     * Configura CORS, desativa CSRF, define regras de autorização e adiciona o filtro JWT.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Habilita CORS com configurações padrão
                .cors(Customizer.withDefaults())
                // Desativa CSRF, pois JWT não depende de cookies/sessão
                .csrf().disable()
                // Define regras de autorização
                .authorizeHttpRequests()
                // Permite acesso público às rotas de autenticação (ex: login, registro)
                .requestMatchers("/auth/register", "/auth/login", "/img/**", "/avaliacao/**").permitAll()
                // Exige autenticação para todas as outras rotas
                .anyRequest().authenticated()
                .and()
                // Adiciona o filtro JWT antes do filtro padrão de autenticação
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Retorna a cadeia de filtros configurada
        return http.build();
    }

    /**
     * Expõe o AuthenticationManager como bean.
     * Usado para autenticar usuários com base nas configurações definidas.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean para codificação de senhas usando BCrypt.
     * Recomendado por ser seguro contra ataques de força bruta.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
