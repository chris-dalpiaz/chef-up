package com.entra21.chef_up.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Classe de configuração para habilitar e personalizar o CORS (Cross-Origin Resource Sharing).
 * O CORS permite que aplicações frontend (como React, Angular, etc.) façam requisições para o backend
 * mesmo estando em domínios diferentes.
 */
public class CorsConfig {

    /**
     * Define um bean que configura as permissões de CORS para a aplicação.
     *
     * @return CorsConfigurationSource com as regras definidas
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permite requisições de qualquer origem (não recomendado em produção)
        // Em produção, substitua "*" pelo domínio específico do frontend, ex: "http://localhost:3000"
        configuration.setAllowedOrigins(Arrays.asList("*"));

        // Define os métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permite todos os headers nas requisições
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Define se credenciais (cookies, headers de autenticação) podem ser enviadas
        // false = não permite envio de credenciais
        configuration.setAllowCredentials(false);

        // Aplica essa configuração para todas as rotas da aplicação
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
