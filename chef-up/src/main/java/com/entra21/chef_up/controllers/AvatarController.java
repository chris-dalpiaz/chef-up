package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Avatar.AvatarRequest;
import com.entra21.chef_up.dtos.Avatar.AvatarResponse;
import com.entra21.chef_up.entities.Avatar;
import com.entra21.chef_up.repositories.AvatarRepository;
import com.entra21.chef_up.services.AvatarService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/avatares")
public class AvatarController {
    /**
     * Repositório para acesso ao banco de dados dos avatares
     */
    private final AvatarService avatarService;

    /**
     * Construtor com injeção de dependência do repositório
     * Permite a classe usar o avatarRepository para CRUD no banco
     */
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    /**
     * Lista todos os avatares cadastrados no sistema.
     *
     * @return lista de objetos Avatar
     */
    @GetMapping
    public List<AvatarResponse> listarAvatares() {
        return avatarService.listarTodos();
    }

    /**
     * Busca um avatar pelo ID.
     * Caso não encontre, retorna erro 404 NOT FOUND.
     *
     * @param idAvatar ID do avatar a ser buscado na URL
     * @return objeto Avatar encontrado
     */
    @GetMapping("/{idAvatar}")
    public AvatarResponse buscarAvatar(@PathVariable Integer idAvatar) {
        return avatarService.buscar(idAvatar);
    }

    /**
     * Cria um novo avatar.
     * Recebe os dados no corpo da requisição no formato JSON.
     *
     * @return avatar criado com ID gerado pelo banco
     */
    @PostMapping
    public AvatarResponse criarAvatar(@RequestBody AvatarRequest request) {
        return avatarService.criar(request);
    }

    /**
     * Atualiza um avatar existente.
     * Se não encontrar o avatar pelo ID, retorna erro 404.
     *
     * @param idAvatar ID do avatar que será atualizado (URL)
     * @return avatar atualizado
     */
    @PutMapping("/{idAvatar}")
    public AvatarResponse alterarAvatar(
            @PathVariable Integer idAvatar,
            @RequestBody AvatarRequest request
    ) {
        return avatarService.alterar(idAvatar, request);
    }

    /**
     * Remove um avatar pelo ID.
     * Retorna o avatar removido para confirmação.
     * Se não encontrar, retorna erro 404.
     *
     * @param idAvatar ID do avatar a ser removido (URL)
     * @return avatar removido
     */
    @DeleteMapping("/{idAvatar}")
    public AvatarResponse removerAvatar(@PathVariable Integer idAvatar) {
        return avatarService.remover(idAvatar);
    }
}
