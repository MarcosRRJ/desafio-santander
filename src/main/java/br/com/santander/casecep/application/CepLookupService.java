package br.com.santander.casecep.application;

import br.com.santander.casecep.domain.CepClient;
import br.com.santander.casecep.domain.CepQueryLog;
import br.com.santander.casecep.domain.CepQueryLogRepository;
import br.com.santander.casecep.domain.port.QueryLogArchivePort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CepLookupService {
    private final CepClient client;
    private final CepQueryLogRepository repository;
    private final QueryLogArchivePort archivePort;

    public CepLookupService(CepClient client, CepQueryLogRepository repository, QueryLogArchivePort archivePort) {
        this.client = client;
        this.repository = repository;
        this.archivePort = archivePort;
    }

    public CepResponse execute(String cep) {
        validateCep(cep);
        CepResponse response = client.fetchByCep(cep);
        CepQueryLog log = repository.save(new CepQueryLog(cep, OffsetDateTime.now(), 200,
                String.format("{\"cep\":\"%s\",\"logradouro\":\"%s\",\"bairro\":\"%s\",\"localidade\":\"%s\",\"uf\":\"%s\"}",
                        response.cep(), response.logradouro(), response.bairro(), response.localidade(), response.uf())));
        archivePort.archive(log);
        return response;
    }

    private void validateCep(String cep) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP deve conter 8 dígitos numéricos");
        }
    }
}
