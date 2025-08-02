package com.cepsearch.cep_search_application.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CepSearchResponse {
    
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    private String logMessage;
    private Boolean erro;

    public CepSearchResponse(String cep, Boolean erro, String logMessage) {
        this.cep = cep;
        this.erro = erro;
        this.logMessage = logMessage;
    }
}
