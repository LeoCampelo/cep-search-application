package com.cepsearch.cep_search_application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cepsearch.cep_search_application.model.ConsultCepLog;
import com.cepsearch.cep_search_application.repository.ConsultCepLogRepository;

@Service
public class ConsultCepLogService {
    
    @Autowired
    private ConsultCepLogRepository consultaLogRepository;

    public ConsultCepLog saveLog(ConsultCepLog log) {
        log.setDataConsulta(LocalDateTime.now());
        return consultaLogRepository.save(log);
    }

    public Page<ConsultCepLog> getAllLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return consultaLogRepository.findAll(pageable);
    }
}
