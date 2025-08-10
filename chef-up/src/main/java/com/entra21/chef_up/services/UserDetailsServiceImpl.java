package com.entra21.chef_up.services;

import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository repository;

    /**
     * Construtor com injeção de dependência do repositório de usuários.
     */
    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca um usuário pelo email para autenticação.
     * Lança exceção se o usuário não for encontrado.
     *
     * @param email Email usado como username para login
     * @return UserDetails com dados para autenticação
     * @throws UsernameNotFoundException se o usuário não existir
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(usuario.getEmail())           /// Define o email como username
                .password(usuario.getSenhaHash())       /// Define a senha (hash) do usuário
                .authorities("USER")                     /// Define a autoridade padrão (não usada)
                .build();
    }
}
