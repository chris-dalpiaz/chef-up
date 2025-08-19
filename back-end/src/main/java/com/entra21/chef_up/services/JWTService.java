package com.entra21.chef_up.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Serviço responsável por gerar e validar tokens JWT.
 */
@Component
public class JWTService {

    // Chave secreta usada para assinar o token JWT
    private final String SECRET_KEY = "Xy7mLp9Vq3Bz5n8Et1WkCJh0ZdYaULuOFvGTMRiNPKxWo6";

    /**
     * Gera um token JWT para o usuário fornecido.
     * O token expira em 1 hora.
     *
     * @param userDetails informações do usuário a serem incluídas no token
     * @return token JWT como String
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Define o nome de usuário como assunto
                .setIssuedAt(new Date()) // Data de emissão do token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expiração em 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Assina o token
                .compact();
    }

    /**
     * Extrai o nome de usuário de um token JWT.
     *
     * @param token token JWT
     * @return nome de usuário extraído
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida o token com base nas informações do usuário fornecido.
     *
     * @param token       token JWT
     * @param userDetails informações do usuário para validação
     * @return true se o token for válido e não estiver expirado
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Verifica se o token JWT está expirado.
     *
     * @param token token JWT
     * @return true se estiver expirado, false caso contrário
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
