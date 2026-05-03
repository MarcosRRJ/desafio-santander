package br.com.santander.casecep.domain;

import br.com.santander.casecep.application.CepResponse;

public interface CepClient {
    CepResponse fetchByCep(String cep);
}
