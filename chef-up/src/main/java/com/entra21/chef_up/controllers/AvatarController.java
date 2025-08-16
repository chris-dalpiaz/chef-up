package com.entra21.chef_up.controllers;

import com.entra21.chef_up.dtos.Avatar.AvatarRequest;
import com.entra21.chef_up.dtos.Avatar.AvatarResponse;
import com.entra21.chef_up.services.AvatarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelas operações HTTP relacionadas à entidade Avatar.
 */
@RestController
@RequestMapping("/avatares")
public class AvatarController {

    private final AvatarService avatarService;

    /**
     * Construtor com injeção de dependência do serviço de avatar.
     */
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    /**
     * Lista todos os avatares cadastrados no sistema.
     *
     * @return lista de objetos AvatarResponse
     */
    @GetMapping
    public List<AvatarResponse> listAvatars() {
        return avatarService.listAll();
    }

    /**
     * Busca um avatar pelo ID.
     * Caso não encontre, retorna erro 404 NOT FOUND.
     *
     * @param idAvatar ID do avatar a ser buscado na URL
     * @return objeto AvatarResponse encontrado
     */
    @GetMapping("/{idAvatar}")
    public AvatarResponse getAvatar(@PathVariable Integer idAvatar) {
        return avatarService.getById(idAvatar);
    }

    /**
     * Cria um novo avatar.
     * Recebe os dados no corpo da requisição no formato JSON.
     *
     * @param request dados do novo avatar
     * @return avatar criado com ID gerado pelo banco
     */
    @PostMapping
    public AvatarResponse createAvatar(@RequestBody AvatarRequest request) {
        return avatarService.create(request);
    }

    /**
     * Atualiza um avatar existente.
     * Se não encontrar o avatar pelo ID, retorna erro 404.
     *
     * @param idAvatar ID do avatar que será atualizado (URL)
     * @param request  novos dados do avatar
     * @return avatar atualizado
     */
    @PutMapping("/{idAvatar}")
    public AvatarResponse updateAvatar(@PathVariable Integer idAvatar,
                                       @RequestBody AvatarRequest request) {
        return avatarService.update(idAvatar, request);
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
    public AvatarResponse deleteAvatar(@PathVariable Integer idAvatar) {
        return avatarService.delete(idAvatar);
    }
}
