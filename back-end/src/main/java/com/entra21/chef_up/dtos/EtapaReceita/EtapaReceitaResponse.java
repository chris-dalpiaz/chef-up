package com.entra21.chef_up.dtos.EtapaReceita;

public class EtapaReceitaResponse {

    private Integer id;

    private Integer ordem;

    private String conteudo;

    private String imagemEtapa;

    public String getImagemEtapa() {
        return imagemEtapa;
    }

    public void setImagemEtapa(String imagemEtapa) {
        this.imagemEtapa = imagemEtapa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
