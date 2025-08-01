package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Titulos;
import com.entra21.chef_up.entities.Usuarios;
import com.entra21.chef_up.repository.UsuariosRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    private final UsuariosRepository usuariosRepository;

    public UsuariosController(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @GetMapping
    public List<Usuarios> listar(){
        return this.usuariosRepository.findAll();
    }

    @GetMapping("/{idUsuario}")
    public Usuarios buscarUsuario(@PathVariable Integer idUsuario){
        return this.usuariosRepository.findById(idUsuario).get();
    }

    @PostMapping
    public Usuarios criarUsuario(@RequestBody Usuarios usuario) {
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
        alterar.setDataCadastro(usuario.getDataCadastro());

        this.usuariosRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idUsuario}")
    public Usuarios removerUsuario(@PathVariable Integer idUsuario) {
        Usuarios usuario = this.usuariosRepository.findById(idUsuario).get();

        this.usuariosRepository.deleteById(idUsuario);

        return usuario;
    }
}
