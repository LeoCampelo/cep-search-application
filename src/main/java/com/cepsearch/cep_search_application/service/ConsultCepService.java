package com.cepsearch.cep_search_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cepsearch.cep_search_application.client.dto.CepSearchResponse;
import com.cepsearch.cep_search_application.model.ConsultCepLog;

@Service
public class ConsultCepService {
    
    @Autowired
    private ConsultCepLogService cepLogService;

    @Autowired
    private WebClient webClient;

    public ResponseEntity<CepSearchResponse> consultCep(String cep) {

        CepSearchResponse response;
        HttpStatus status = HttpStatus.OK;
        
        try {
            response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                        .path("/{cep}/json/")
                        .build(cep))
                        .retrieve()
                        .bodyToMono(CepSearchResponse.class)
                        .block();
            
            if(response != null && Boolean.TRUE.equals(response.getErro())) {
                response.setCep(cep);
                response.setLogMessage("CEP inválido ou não encontrado.");
            } else {
                response.setLogMessage("CEP encontrado com sucesso!");
            }

        } catch (Exception e) {
            String errorMessage = "Erro ao consultar CEP: " + cep + ". Detalhes: " + e.getMessage();
            response = new CepSearchResponse(cep, Boolean.TRUE, errorMessage);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        cepLogService.saveLog(new ConsultCepLog(response));
        return ResponseEntity.status(status).body(response);
    }
}
