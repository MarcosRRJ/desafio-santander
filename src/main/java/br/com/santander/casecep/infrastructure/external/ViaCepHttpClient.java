package br.com.santander.casecep.infrastructure.external;

import br.com.santander.casecep.application.CepResponse;
import br.com.santander.casecep.domain.CepClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ViaCepHttpClient implements CepClient {
    private final RestClient restClient;

    public ViaCepHttpClient(@Value("${cep.api.base-url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public CepResponse fetchByCep(String cep) {
        ViaCepResponse resp = restClient.get().uri("/{cep}/json", cep)
                .retrieve()
                .body(ViaCepResponse.class);
        return new CepResponse(resp.cep(), resp.logradouro(), resp.bairro(), resp.localidade(), resp.uf());
    }

    private record ViaCepResponse(String cep, String logradouro, String bairro, String localidade, String uf) {}
}
