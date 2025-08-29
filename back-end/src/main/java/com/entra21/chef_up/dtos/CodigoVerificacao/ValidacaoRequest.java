package com.entra21.chef_up.dtos.CodigoVerificacao;

public class ValidacaoRequest {

    private String digitCode;

    private String email;

    public String getDigitCode() {
        return digitCode;
    }

    public void setDigitCode(String digitCode) {
        this.digitCode = digitCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
