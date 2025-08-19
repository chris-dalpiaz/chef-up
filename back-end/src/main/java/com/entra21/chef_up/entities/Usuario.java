package com.entra21.chef_up.entities;

import com.entra21.chef_up.dtos.TituloUsuario.TituloUsuarioResponse;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    private String senha;

    private LocalDateTime dataCadastro;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProgressoUsuario progressoUsuario;

    @ManyToOne()
    @JoinColumn(name = "pronome_id")
    private Pronome pronome;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<TituloUsuario> titulos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<AdjetivoUsuario> adjetivos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ReceitaUsuario> receitasConcluidas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<IngredienteUsuario> ingredientes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<AvatarUsuario> avatares = new ArrayList<>();

    public List<TituloUsuario> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<TituloUsuario> titulos) {
        this.titulos = titulos;
    }

    public List<AdjetivoUsuario> getAdjetivos() {
        return adjetivos;
    }

    public void setAdjetivos(List<AdjetivoUsuario> adjetivos) {
        this.adjetivos = adjetivos;
    }

    public List<ReceitaUsuario> getReceitasConcluidas() {
        return receitasConcluidas;
    }

    public void setReceitasConcluidas(List<ReceitaUsuario> receitasConcluidas) {
        this.receitasConcluidas = receitasConcluidas;
    }

    public List<IngredienteUsuario> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteUsuario> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<AvatarUsuario> getAvatares() {
        return avatares;
    }

    public void setAvatares(List<AvatarUsuario> avatares) {
        this.avatares = avatares;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ProgressoUsuario getProgressoUsuario() {
        return progressoUsuario;
    }

    public void setProgressoUsuario(ProgressoUsuario progressoUsuario) {
        this.progressoUsuario = progressoUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Pronome getPronome() {
        return pronome;
    }

    public void setPronome(Pronome pronome) {
        this.pronome = pronome;
    }
}
