package com.cepsearch.cep_search_application.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cepsearch.cep_search_application.model.ConsultCepLog;
import com.cepsearch.cep_search_application.service.ConsultCepLogService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/cep/log")
public class ConsultCepLogController {
    
    @Autowired
    private ConsultCepLogService cepLogService;

    @GetMapping
    public Page<ConsultCepLog> getConsultAllLogs(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

        return cepLogService.getAllLogs(page, size);
    }

    @GetMapping("/by-date")
    public Page<ConsultCepLog> getLogsByDateRange(
                        @RequestParam("start") String start,
                        @RequestParam("end") String end,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {
        
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        
        return cepLogService.getLogsByDateRange(startDate, endDate, page, size);
    }
}
