package com.entra21.chef_up.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTService {
    /// Chave secreta usada para assinar o token JWT
    private final String SECRET = "Xy7mLp9Vq3Bz5n8Et1WkCJh0ZdYaULuOFvGTMRiNPKxWo6";

    /**
     * Gera um token JWT para o usuário fornecido.
     * O token expira em 1 hora.
     *
     * @param userDetails informações do usuário para incluir no token
     * @return token JWT como String
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) /// Define o usuário (username) no token
                .setIssuedAt(new Date()) /// Data de emissão do token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) /// Expiração em 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET) /// Assina o token com algoritmo HS256
                .compact();
    }

    /**
     * Extrai o nome de usuário do token JWT.
     *
     * @param token token JWT
     * @return nome de usuário extraído do token
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET) /// Usa a chave secreta para validar token
                .parseClaimsJws(token) /// Decodifica token
                .getBody()
                .getSubject(); /// Retorna o "subject" (username)
    }

    /**
     * Verifica se o token é válido para o usuário informado.
     *
     * @param token token JWT
     * @param userDetails informações do usuário para validar
     * @return true se token é válido e não expirou, false caso contrário
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Verifica se o token JWT está expirado.
     *
     * @param token token JWT
     * @return true se token expirou, false caso contrário
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date()); /// Compara data de expiração com data atual
    }
}
