package com.cepsearch.cep_search_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cepsearch.cep_search_application.model.ConsultCepLog;
import com.cepsearch.cep_search_application.service.ConsultCepLogService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/cep/log")
public class ConsultCepLogController {
    
    @Autowired
    private ConsultCepLogService cepService;

    @GetMapping
    public List<ConsultCepLog> getConsultAllLogs() {
        return cepService.getAllLogs();
    }
}
