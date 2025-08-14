package com.entra21.chef_up.dtos.EtapaReceita;

public class EtapaReceitaRequest {
    private Integer idReceita;

    /// Ordem da etapa na receita (ex: 1, 2, 3...)
    private Integer ordem;

    /// Conteúdo/instruções da etapa
    private String conteudo;

    public Integer getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(Integer idReceita) {
        this.idReceita = idReceita;
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
