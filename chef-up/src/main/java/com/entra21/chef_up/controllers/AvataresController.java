package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Avatares;
import com.entra21.chef_up.repository.AvataresRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avatares")
public class AvataresController {
    private final AvataresRepository avataresRepository;

    public AvataresController(AvataresRepository avataresRepository) {
        this.avataresRepository = avataresRepository;
    }

    @GetMapping
    public List<Avatares> listar(){
        return this.avataresRepository.findAll();
    }

    @GetMapping("/{idAvatar}")
    public Avatares buscarAvatar(@PathVariable Integer idAvatar){
        return this.avataresRepository.findById(idAvatar).get();
    }

    @PostMapping
    public Avatares criarAvatar(@RequestBody Avatares avatar) {
        this.avataresRepository.save(avatar);
        return avatar;
    }

    @PutMapping("/{idAvatar}")
    public Avatares alterarAvatar(
            @PathVariable Integer idAvatar,
            @RequestBody Avatares avatar
    ) {
        Avatares alterar = this.avataresRepository.findById(idAvatar).get();

        alterar.setNome(avatar.getNome());
        alterar.setImagemUrl(avatar.getImagemUrl());

        this.avataresRepository.save(alterar);

        return alterar;
    }

    @DeleteMapping("/{idAvatar}")
    public Avatares removerAvatar(@PathVariable Integer idAvatar) {
        Avatares avatar = this.avataresRepository.findById(idAvatar).get();

        this.avataresRepository.deleteById(idAvatar);

        return avatar;
    }
}
