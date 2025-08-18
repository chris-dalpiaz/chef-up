package com.entra21.chef_up.services;

import com.entra21.chef_up.entities.Usuario;
import com.entra21.chef_up.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por carregar os detalhes do usuário para autenticação.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository userRepository;

    /**
     * Construtor com injeção de dependência do repositório de usuários.
     */
    public UserDetailsServiceImpl(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carrega um usuário pelo e-mail para fins de autenticação.
     * Lança exceção se o usuário não for encontrado.
     *
     * @param email E-mail utilizado como nome de usuário no login
     * @return UserDetails com os dados de autenticação
     * @throws UsernameNotFoundException se o usuário não existir
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(user.getEmail())           // Define o e-mail como nome de usuário
                .password(user.getSenha())       // Define a senha criptografada
                .authorities("USER")                 // Autoridade padrão (não utilizada)
                .build();
    }
}
