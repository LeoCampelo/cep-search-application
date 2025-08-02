package com.cepsearch.cep_search_application.service;

import com.cepsearch.cep_search_application.client.dto.CepSearchResponse;
import com.cepsearch.cep_search_application.model.ConsultCepLog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.function.Function;

class ConsultCepServiceTest {

    @Mock
    private ConsultCepLogService cepLogService;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private ConsultCepService consultCepService;

    @SuppressWarnings("rawtypes")
    private RequestHeadersUriSpec requestHeadersUriSpec = mock(RequestHeadersUriSpec.class);

    @SuppressWarnings("rawtypes")
    private RequestHeadersSpec requestHeadersSpec = mock(RequestHeadersSpec.class);

    private ResponseSpec responseSpec = mock(ResponseSpec.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void consultCep_shouldReturnSuccess_whenCepFound() {
        String cep = "01001000";
        CepSearchResponse mockResponse = new CepSearchResponse();
        mockResponse.setErro(false);
        
        when(responseSpec.bodyToMono(CepSearchResponse.class)).thenReturn(Mono.just(mockResponse));

        ResponseEntity<CepSearchResponse> response = consultCepService.consultCep(cep);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLogMessage()).isEqualTo("CEP encontrado com sucesso!");
        verify(cepLogService).saveLog(any(ConsultCepLog.class));
    }

    @Test
    void consultCep_shouldReturnError_whenCepNotFound() {
        String cep = "99999999";
        CepSearchResponse mockResponse = new CepSearchResponse();
        mockResponse.setErro(true);

        when(responseSpec.bodyToMono(CepSearchResponse.class)).thenReturn(Mono.just(mockResponse));

        ResponseEntity<CepSearchResponse> response = consultCepService.consultCep(cep);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLogMessage()).isEqualTo("CEP inválido ou não encontrado.");
        verify(cepLogService).saveLog(any(ConsultCepLog.class));
    }

    @Test
    void consultCep_shouldReturnInternalServerError_whenExceptionOccurs() {
        String cep = "12345678";

        when(responseSpec.bodyToMono(CepSearchResponse.class)).thenThrow(new RuntimeException("Erro de conexão"));

        ResponseEntity<CepSearchResponse> response = consultCepService.consultCep(cep);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().getLogMessage()).contains("Erro ao consultar CEP");
        verify(cepLogService).saveLog(any(ConsultCepLog.class));
    }

}