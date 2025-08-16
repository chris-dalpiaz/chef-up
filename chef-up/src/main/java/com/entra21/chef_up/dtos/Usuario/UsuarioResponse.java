package com.entra21.chef_up.dtos.Usuario;

import com.entra21.chef_up.dtos.AdjetivoUsuario.AdjetivoUsuarioResponse;
import com.entra21.chef_up.dtos.AvatarUsuario.AvatarUsuarioResponse;
import com.entra21.chef_up.dtos.IngredienteUsuario.IngredienteUsuarioResponse;
import com.entra21.chef_up.dtos.ProgressoUsuario.ProgressoUsuarioResponse;
import com.entra21.chef_up.dtos.Pronome.PronomeResponse;
import com.entra21.chef_up.dtos.ReceitaUsuario.ReceitaUsuarioResponse;
import com.entra21.chef_up.dtos.TituloUsuario.TituloUsuarioResponse;

import java.util.List;

public class UsuarioResponse {

    private String nome;

    private String email;

    private PronomeResponse pronome;

    private List<TituloUsuarioResponse> titulos;

    private List<AdjetivoUsuarioResponse> adjetivos;

    private List<ReceitaUsuarioResponse> receitasConcluidas;

    private ProgressoUsuarioResponse progresso;

    private List<IngredienteUsuarioResponse> ingredientes;

    private List<AvatarUsuarioResponse> avatares;

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

    public PronomeResponse getPronome() {
        return pronome;
    }

    public void setPronome(PronomeResponse pronome) {
        this.pronome = pronome;
    }

    public List<TituloUsuarioResponse> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<TituloUsuarioResponse> titulos) {
        this.titulos = titulos;
    }

    public List<AdjetivoUsuarioResponse> getAdjetivos() {
        return adjetivos;
    }

    public void setAdjetivos(List<AdjetivoUsuarioResponse> adjetivos) {
        this.adjetivos = adjetivos;
    }

    public List<ReceitaUsuarioResponse> getReceitasConcluidas() {
        return receitasConcluidas;
    }

    public void setReceitasConcluidas(List<ReceitaUsuarioResponse> receitasConcluidas) {
        this.receitasConcluidas = receitasConcluidas;
    }

    public ProgressoUsuarioResponse getProgresso() {
        return progresso;
    }

    public void setProgresso(ProgressoUsuarioResponse progresso) {
        this.progresso = progresso;
    }

    public List<IngredienteUsuarioResponse> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteUsuarioResponse> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<AvatarUsuarioResponse> getAvatares() {
        return avatares;
    }

    public void setAvatares(List<AvatarUsuarioResponse> avatares) {
        this.avatares = avatares;
    }
}