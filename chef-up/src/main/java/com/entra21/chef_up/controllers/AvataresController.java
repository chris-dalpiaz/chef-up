package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Avatares;
import com.entra21.chef_up.repository.AvataresRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/avatares")
public class AvataresController {

    private final AvataresRepository avataresRepository;

    // Construtor com injeção de dependência
    public AvataresController(AvataresRepository avataresRepository) {
        this.avataresRepository = avataresRepository;
    }

    /**
     * Lista todos os avatares cadastrados.
     */
    @GetMapping
    public List<Avatares> listar() {
        return avataresRepository.findAll();
    }

    /**
     * Busca um avatar específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idAvatar}")
    public Avatares buscarAvatar(@PathVariable Integer idAvatar) {
        return avataresRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));
    }

    /**
     * Cria um novo avatar.
     */
    @PostMapping
    public Avatares criarAvatar(@RequestBody Avatares avatar) {
        return avataresRepository.save(avatar);
    }

    /**
     * Atualiza os dados de um avatar existente.
     * Retorna 404 se o avatar não existir.
     */
    @PutMapping("/{idAvatar}")
    public Avatares alterarAvatar(
            @PathVariable Integer idAvatar,
            @RequestBody Avatares avatar
    ) {
        Avatares alterar = avataresRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        alterar.setNome(avatar.getNome());
        alterar.setImagemUrl(avatar.getImagemUrl());

        return avataresRepository.save(alterar);
    }

    /**
     * Remove um avatar pelo ID.
     * Retorna o avatar removido ou 404 se não existir.
     */
    @DeleteMapping("/{idAvatar}")
    public Avatares removerAvatar(@PathVariable Integer idAvatar) {
        Avatares avatar = avataresRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        avataresRepository.deleteById(idAvatar);

        return avatar;
    }
}
