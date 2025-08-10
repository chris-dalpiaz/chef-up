package com.entra21.chef_up.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class TituloUsuario {
    /// ID único da associação entre título e usuário
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /// Referência ao título associado
    @ManyToOne()
    @JoinColumn(name = "titulos_id")
    private Titulo titulo;

    /// Referência ao usuário associado
    @ManyToOne()
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    /// Data que o título foi desbloqueado pelo usuário
    private LocalDateTime desbloqueadoEm;

    /** Retorna o ID da associação */
    public Integer getId() {
        return id;
    }

    /** Define o ID da associação */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Retorna o título associado */
    public Titulo getTitulo() {
        return titulo;
    }

    /** Define o título associado */
    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    /** Retorna o usuário associado */
    public Usuario getUsuario() {
        return usuario;
    }

    /** Define o usuário associado */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** Retorna a data que o título foi desbloqueado */
    public LocalDateTime getDesbloqueadoEm() {
        return desbloqueadoEm;
    }

    /** Define a data que o título foi desbloqueado */
    public void setDesbloqueadoEm(LocalDateTime desbloqueadoEm) {
        this.desbloqueadoEm = desbloqueadoEm;
    }
}
