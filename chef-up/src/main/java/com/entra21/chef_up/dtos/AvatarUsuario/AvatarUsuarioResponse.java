package com.entra21.chef_up.dtos.AvatarUsuario;

import com.entra21.chef_up.dtos.Avatar.AvatarResponse;

public class AvatarUsuarioResponse {

    private AvatarResponse avatar;

    private Boolean estaAtivo;

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
