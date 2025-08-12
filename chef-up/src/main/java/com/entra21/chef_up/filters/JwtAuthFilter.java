package com.entra21.chef_up.filters;

import com.entra21.chef_up.services.JWTService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Construtor com injeção do serviço JWT e serviço de detalhes do usuário.
     */
    public JwtAuthFilter(JWTService jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Método principal que intercepta a requisição para validar o token JWT.
     *
     * @param request  Requisição HTTP
     * @param response Resposta HTTP
     * @param chain    Cadeia de filtros
     * @throws ServletException Exceção no filtro
     * @throws IOException      Exceção de IO
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        /// Obtém o header Authorization da requisição
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        /// Verifica se o header existe e começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // Extrai o token (removendo "Bearer ")
            username = jwtUtil.extractUsername(token);  // Extrai o username do token
        }

        /// Se username existe e ainda não está autenticado no contexto do Spring Security
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            /// Carrega os detalhes do usuário pelo username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            /// Verifica se o token é válido para o usuário carregado
            if (jwtUtil.isTokenValid(token, userDetails)) {
                /// Cria token de autenticação para o Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                /// Define detalhes da autenticação com a requisição atual
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                /// Seta o contexto de segurança com a autenticação criada
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        /// Continua a cadeia de filtros da requisição
        chain.doFilter(request, response);
    }
}
