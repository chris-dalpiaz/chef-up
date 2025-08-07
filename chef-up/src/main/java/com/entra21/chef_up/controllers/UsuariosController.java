package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.AdjetivoUsuario;
import com.entra21.chef_up.entities.Usuarios;
import com.entra21.chef_up.repository.AdjetivoUsuarioRepository;
import com.entra21.chef_up.repository.AdjetivosRepository;
import com.entra21.chef_up.repository.UsuariosRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    private final UsuariosRepository usuariosRepository;
    private final AdjetivosRepository adjetivosRepository;
    private final AdjetivoUsuarioRepository adjetivoUsuarioRepository;

    public UsuariosController(UsuariosRepository usuariosRepository, AdjetivosRepository adjetivosRepository, AdjetivoUsuarioRepository adjetivoUsuarioRepository) {
        this.usuariosRepository = usuariosRepository;
        this.adjetivosRepository = adjetivosRepository;
        this.adjetivoUsuarioRepository = adjetivoUsuarioRepository;
    }

    @GetMapping
    public List<Usuarios> listar() {
        return this.usuariosRepository.findAll();
    }

    @GetMapping("/{idUsuario}")
    public Usuarios buscarUsuario(@PathVariable Integer idUsuario) {
        return this.usuariosRepository.findById(idUsuario).get();
    }

    @PostMapping
    public Usuarios criarUsuario(@RequestBody Usuarios usuario) {
        usuario.setDataCadastro(LocalDateTime.now());
        this.usuariosRepository.save(usuario);
        return usuario;
    }

    @PutMapping("/{idUsuario}")
    public Usuarios alterarUsuario(
            @PathVariable Integer idUsuario,
            @RequestBody Usuarios usuario
    ) {
        Usuarios alterar = this.usuariosRepository.findById(idUsuario).get();

        alterar.setNome(usuario.getNome());
        alterar.setEmail(usuario.getEmail());
        alterar.setSenhaHash(usuario.getSenhaHash());
        alterar.setPronome(usuario.getPronome());

        this.usuariosRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idUsuario}")
    public Usuarios removerUsuario(@PathVariable Integer idUsuario) {
        Usuarios usuario = this.usuariosRepository.findById(idUsuario).get();

        this.usuariosRepository.deleteById(idUsuario);

        return usuario;
    }

    @GetMapping("/{idUsuario}/adjetivos")
    public List<AdjetivoUsuario> listarAdjetivos(@PathVariable Integer idUsuario) {
        return this.adjetivoUsuarioRepository.findByUsuarioId(idUsuario);
    }

    @GetMapping("/{idUsuario}/adjetivos/{idAdjetivo}")
    public AdjetivoUsuario buscarAdjetivo(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idAdjetivo
    ) {

        Usuarios usuario = this.usuariosRepository.findById(idUsuario).get();
        AdjetivoUsuario adjetivo = this.adjetivoUsuarioRepository.findById(idAdjetivo).get();

        if (usuario != null && adjetivo != null && adjetivo.getUsuario().getId().equals(idUsuario)) {
            return adjetivo;
        }
        return null;
    }

    @PostMapping("/{idUsuario}/adjetivos")
    public AdjetivoUsuario criarAdjetivoUsuario(
            @PathVariable Integer idUsuario,
            @RequestBody AdjetivoUsuario adjetivoUsuario) {

        adjetivoUsuario.setUsuario(this.usuariosRepository.findById(idUsuario).get());
        this.adjetivoUsuarioRepository.save(adjetivoUsuario);
        return adjetivoUsuario;
    }

    @PutMapping("/{idUsuario}/adjetivos")
    public AdjetivoUsuario editarAdjetivoUsuario(
            @PathVariable Integer idUsuario,
            @RequestBody AdjetivoUsuario adjetivoUsuario) {

        AdjetivoUsuario alterar = this.adjetivoUsuarioRepository.findById(idUsuario).get();

        alterar.setUsuario(adjetivoUsuario.getUsuario());
        alterar.setAdjetivo(adjetivoUsuario.getAdjetivo());

        this.adjetivoUsuarioRepository.save(alterar);

        return alterar;
    }

//    @DeleteMapping("/{idUsuario}/adjetivos/{idAdjetivo}")
//    public AdjetivoUsuario removerAdjetivoUsuario(
//            @PathVariable Integer idUsuario,
//            @RequestBody AdjetivoUsuario adjetivoUsuario) {
//
//        AdjetivoUsuario alterar = this.adjetivoUsuarioRepository.findById(idUsuario).get();
//        return alterar;
//    }

}
