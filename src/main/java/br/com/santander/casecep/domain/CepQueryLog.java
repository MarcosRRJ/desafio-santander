package br.com.santander.casecep.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cep_query_log")
public class CepQueryLog {
    @Id
    @GeneratedValue
    private UUID id;
    private String cepConsultado;
    private OffsetDateTime dataHoraConsulta;
    private Integer statusHttpExterno;
    @Column(columnDefinition = "TEXT")
    private String payloadRetorno;

    protected CepQueryLog() {
    }

    public CepQueryLog(String cepConsultado, OffsetDateTime dataHoraConsulta, Integer statusHttpExterno, String payloadRetorno) {
        this.cepConsultado = cepConsultado;
        this.dataHoraConsulta = dataHoraConsulta;
        this.statusHttpExterno = statusHttpExterno;
        this.payloadRetorno = payloadRetorno;
    }

    public UUID getId() { return id; }
}
