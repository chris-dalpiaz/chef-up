package com.entra21.chef_up.dtos.ReceitaUsuario;

import com.entra21.chef_up.dtos.Receita.ReceitaResponse;

import java.time.LocalDateTime;

public class ReceitaUsuarioResponse {

    /// Receita que foi realizada (muitos para um)
    private ReceitaResponse receitaResponse;

    /// Data e hora em que o usuário concluiu a receita
    private LocalDateTime dataConclusao;

    /// URL ou caminho da foto do prato feito pelo usuário
    private String fotoPrato;

    /// Pontuação dada ao prato feito pelo usuário
    private Integer pontuacaoPrato;

    public ReceitaResponse getReceitaResponse() {
        return receitaResponse;
    }

    public void setReceitaResponse(ReceitaResponse receitaResponse) {
        this.receitaResponse = receitaResponse;
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
