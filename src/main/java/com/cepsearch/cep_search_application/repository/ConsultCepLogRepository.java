package com.cepsearch.cep_search_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cepsearch.cep_search_application.model.ConsultCepLog;

@Repository
public interface ConsultCepLogRepository extends JpaRepository<ConsultCepLog, Long> {
    
}
