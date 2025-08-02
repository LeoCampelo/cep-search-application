package com.cepsearch.cep_search_application.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cepsearch.cep_search_application.model.ConsultCepLog;

@Repository
public interface ConsultCepLogRepository extends JpaRepository<ConsultCepLog, Long> {
    
    public Page<ConsultCepLog> findByDataConsultaBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
