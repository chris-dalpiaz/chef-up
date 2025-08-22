package com.entra21.chef_up.dtos.ReceitaUsuario;

import com.entra21.chef_up.dtos.Receita.ReceitaResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ReceitaUsuarioResponse {

    private Integer id;

    private ReceitaResponse receita;

    @JsonFormat(
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS",
            shape   = JsonFormat.Shape.STRING
    )
    private LocalDateTime dataConclusao;

    private String fotoPrato;

    private Integer pontuacaoPrato;

    private String textoAvaliacao;

    public String getTextoAvaliacao() {
        return textoAvaliacao;
    }

    public void setTextoAvaliacao(String textoAvaliacao) {
        this.textoAvaliacao = textoAvaliacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public ReceitaResponse getReceita() {
        return receita;
    }

    public void setReceita(ReceitaResponse receita) {
        this.receita = receita;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getFotoPrato() {
        return fotoPrato;
    }

    public void setFotoPrato(String fotoPrato) {
        this.fotoPrato = fotoPrato;
    }

    public Integer getPontuacaoPrato() {
        return pontuacaoPrato;
    }

    public void setPontuacaoPrato(Integer pontuacaoPrato) {
        this.pontuacaoPrato = pontuacaoPrato;
    }
}
