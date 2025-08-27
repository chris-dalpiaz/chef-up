package com.entra21.chef_up.config;

import com.entra21.chef_up.filters.JwtAuthFilter;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) habilita o CORS usando CorsConfig.corsConfigurationSource()
                .cors(Customizer.withDefaults())
                // 2) desativa CSRF (API stateless com JWT)
                .csrf().disable()
                // 3) define regras de autorização
                .authorizeHttpRequests(auth -> auth
                        // libera todas as requisições OPTIONS (preflight CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // rotas públicas
                        .requestMatchers(
                                "/auth/register",
                                "/auth/login",
                                "/img/**",
                                "/avaliacao/**",
                                "/uploads/**",
                                "/codigos/**"
                        ).permitAll()
                        // todas as outras exigem autenticação
                        .anyRequest().authenticated()
                )
                // 4) adiciona o filtro JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
