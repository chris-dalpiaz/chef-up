package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Avatar;
import com.entra21.chef_up.repository.AvatarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/avatares")
public class AvatarController {

    private final AvatarRepository avatarRepository;

    // Construtor com injeção de dependência
    public AvatarController(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    /**
     * Lista todos os avatares cadastrados.
     */
    @GetMapping
    public List<Avatar> listar() {
        return avatarRepository.findAll();
    }

    /**
     * Busca um avatar específico pelo ID.
     * Retorna 404 se não for encontrado.
     */
    @GetMapping("/{idAvatar}")
    public Avatar buscarAvatar(@PathVariable Integer idAvatar) {
        return avatarRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));
    }

    /**
     * Cria um novo avatar.
     */
    @PostMapping
    public Avatar criarAvatar(@RequestBody Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    /**
     * Atualiza os dados de um avatar existente.
     * Retorna 404 se o avatar não existir.
     */
    @PutMapping("/{idAvatar}")
    public Avatar alterarAvatar(
            @PathVariable Integer idAvatar,
            @RequestBody Avatar avatar
    ) {
        Avatar alterar = avatarRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        alterar.setNome(avatar.getNome());
        alterar.setImagemUrl(avatar.getImagemUrl());

        return avatarRepository.save(alterar);
    }

    /**
     * Remove um avatar pelo ID.
     * Retorna o avatar removido ou 404 se não existir.
     */
    @DeleteMapping("/{idAvatar}")
    public Avatar removerAvatar(@PathVariable Integer idAvatar) {
        Avatar avatar = avatarRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        avatarRepository.deleteById(idAvatar);

        return avatar;
    }
}
