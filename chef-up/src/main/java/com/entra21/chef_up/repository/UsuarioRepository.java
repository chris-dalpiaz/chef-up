package com.entra21.chef_up.repository;

import com.entra21.chef_up.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    <T> ScopedValue<T> findByEmail(String email);
}
