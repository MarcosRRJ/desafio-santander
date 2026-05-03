package br.com.santander.casecep.application;

public record CepResponse(String cep, String logradouro, String bairro, String localidade, String uf) {
}
