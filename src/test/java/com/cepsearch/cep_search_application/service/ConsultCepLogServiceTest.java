package com.cepsearch.cep_search_application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

import com.cepsearch.cep_search_application.model.ConsultCepLog;
import com.cepsearch.cep_search_application.repository.ConsultCepLogRepository;

public class ConsultCepLogServiceTest {
    
    @Mock
    private ConsultCepLogRepository consultCepLogRepository;

    @InjectMocks
    private ConsultCepLogService consultCepLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLog_shouldSetDataConsultaAndSave() {
        ConsultCepLog log = new ConsultCepLog();
        ConsultCepLog savedLog = new ConsultCepLog();
        when(consultCepLogRepository.save(any(ConsultCepLog.class))).thenReturn(savedLog);

        ConsultCepLog result = consultCepLogService.saveLog(log);

        assertThat(result).isEqualTo(savedLog);
        assertThat(log.getDataConsulta()).isNotNull();
        verify(consultCepLogRepository).save(log);
    }

    @Test
    void getAllLogs_shouldReturnPagedLogs() {
        Page<ConsultCepLog> page = new PageImpl<>(Collections.emptyList());
        when(consultCepLogRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<ConsultCepLog> result = consultCepLogService.getAllLogs(0, 10);

        assertThat(result).isEqualTo(page);
        verify(consultCepLogRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    void getLogsByDateRange_shouldReturnPagedLogsByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        Page<ConsultCepLog> page = new PageImpl<>(Collections.emptyList());
        when(consultCepLogRepository.findByDataConsultaBetween(eq(start), eq(end), any(Pageable.class))).thenReturn(page);

        Page<ConsultCepLog> result = consultCepLogService.getLogsByDateRange(start, end, 0, 10);

        assertThat(result).isEqualTo(page);
        verify(consultCepLogRepository).findByDataConsultaBetween(start, end, PageRequest.of(0, 10));
    }

}
