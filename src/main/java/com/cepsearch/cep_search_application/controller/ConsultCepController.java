package com.cepsearch.cep_search_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cepsearch.cep_search_application.client.dto.CepSearchResponse;
import com.cepsearch.cep_search_application.service.ConsultCepService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/cep")
public class ConsultCepController {

    @Autowired
    private ConsultCepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<CepSearchResponse> consultCep(@PathVariable String cep) {
        return cepService.consultCep(cep);
    }
    
}
