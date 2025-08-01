package com.cepsearch.cep_search_application.model;

import java.time.LocalDateTime;

import com.cepsearch.cep_search_application.client.dto.CepSearchResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consult_cep_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultCepLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "complemento")
    private String complemento;
    
    @Column(name = "bairro")
    private String bairro;
    
    @Column(name = "localidade")
    private String localidade;
    
    @Column(name = "uf")
    private String uf;

    @Column(name = "ibge")
    private String ibge;

    @Column(name = "gia")
    private String gia;

    @Column(name = "ddd")
    private String ddd;

    @Column(name = "siafi")
    private String siafi;

    @Column(name = "log_message")
    private String logMessage;

    @Column(name = "data_consulta")
    private LocalDateTime dataConsulta;

    public ConsultCepLog(CepSearchResponse response) {
        this.cep = response.getCep();
        this.logradouro = response.getLogradouro();
        this.complemento = response.getComplemento();
        this.bairro = response.getBairro();
        this.localidade = response.getLocalidade();
        this.uf = response.getUf();
        this.ibge = response.getIbge();
        this.gia = response.getGia();
        this.ddd = response.getDdd();
        this.siafi = response.getSiafi();
        this.logMessage = response.getLogMessage();
        this.dataConsulta = LocalDateTime.now();
    }

}
