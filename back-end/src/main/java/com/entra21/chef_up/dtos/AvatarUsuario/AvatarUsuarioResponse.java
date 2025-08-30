package com.entra21.chef_up.dtos.AvatarUsuario;

import com.entra21.chef_up.dtos.Avatar.AvatarResponse;
import com.entra21.chef_up.entities.AvatarUsuario;

public class AvatarUsuarioResponse {

    private Integer id;

    private AvatarResponse avatar;

    private Boolean estaAtivo;

    public AvatarUsuarioResponse() {
    }

    public AvatarUsuarioResponse(AvatarUsuario novo) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getEstaAtivo() {
        return estaAtivo;
    }

    public void setEstaAtivo(Boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
    }

    public AvatarResponse getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarResponse avatar) {
        this.avatar = avatar;
    }
}
