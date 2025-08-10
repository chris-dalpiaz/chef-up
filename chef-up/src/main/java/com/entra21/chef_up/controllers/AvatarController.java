package com.entra21.chef_up.controllers;

import com.entra21.chef_up.entities.Avatar;
import com.entra21.chef_up.repositories.AvatarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/avatares")
public class AvatarController {

    /** Repositório para acesso ao banco de dados dos avatares */
    private final AvatarRepository avatarRepository;

    /**
     * Construtor com injeção de dependência do repositório
     * Permite a classe usar o avatarRepository para CRUD no banco
     */
    public AvatarController(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    /**
     * Lista todos os avatares cadastrados no sistema.
     *
     * @return lista de objetos Avatar
     */
    @GetMapping
    public List<Avatar> listarAvatares() {
        return avatarRepository.findAll();
    }

    /**
     * Busca um avatar pelo ID.
     * Caso não encontre, retorna erro 404 NOT FOUND.
     *
     * @param idAvatar ID do avatar a ser buscado na URL
     * @return objeto Avatar encontrado
     */
    @GetMapping("/{idAvatar}")
    public Avatar buscarAvatar(@PathVariable Integer idAvatar) {
        return avatarRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));
    }

    /**
     * Cria um novo avatar.
     * Recebe os dados no corpo da requisição no formato JSON.
     *
     * @param avatar objeto Avatar a ser criado
     * @return avatar criado com ID gerado pelo banco
     */
    @PostMapping
    public Avatar criarAvatar(@RequestBody Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    /**
     * Atualiza um avatar existente.
     * Se não encontrar o avatar pelo ID, retorna erro 404.
     *
     * @param idAvatar ID do avatar que será atualizado (URL)
     * @param avatar novo conteúdo para o avatar (JSON no corpo)
     * @return avatar atualizado
     */
    @PutMapping("/{idAvatar}")
    public Avatar alterarAvatar(
            @PathVariable Integer idAvatar,
            @RequestBody Avatar avatar
    ) {
        /// Busca o avatar atual para atualização
        Avatar alterar = avatarRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        /// Atualiza os campos permitidos
        alterar.setNome(avatar.getNome());
        alterar.setImagemUrl(avatar.getImagemUrl());

        /// Salva as alterações no banco
        return avatarRepository.save(alterar);
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
    public Avatar removerAvatar(@PathVariable Integer idAvatar) {
        Avatar avatar = avatarRepository.findById(idAvatar)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));

        /// Deleta o avatar do banco
        avatarRepository.deleteById(idAvatar);

        /// Retorna o avatar excluído
        return avatar;
    }
}
