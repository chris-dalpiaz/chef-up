package com.entra21.chef_up.dtos.Avatar;

public class AvatarRequest {
    /// Nome do avatar
    private String nome;

    /// URL da imagem do avatar
    private String imagemUrl;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
