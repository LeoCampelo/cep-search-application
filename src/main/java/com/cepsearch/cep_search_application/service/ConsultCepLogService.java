package com.cepsearch.cep_search_application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cepsearch.cep_search_application.model.ConsultCepLog;
import com.cepsearch.cep_search_application.repository.ConsultCepLogRepository;

@Service
public class ConsultCepLogService {
    
    @Autowired
    private ConsultCepLogRepository consultaLogRepository;

    public ConsultCepLog saveLog(ConsultCepLog log) {
        return consultaLogRepository.save(saveLog(log));
    }

    public List<ConsultCepLog> getAllLogs() {
        return consultaLogRepository.findAll();
    }
}
