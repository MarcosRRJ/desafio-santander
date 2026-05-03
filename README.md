# Case Técnico — API de Consulta de CEP (Java 21 + Spring Boot)

Implementação completa do case solicitado:
- Consulta de CEP em API externa mockada via WireMock.
- Persistência de logs de consulta (horário + payload retornado + status externo).
- Princípios SOLID com separação por camadas e portas/adapters.
- Testes unitários e de integração.
- Docker Compose para PostgreSQL + WireMock.
- Healthcheck com Actuator.
- Pipeline CI com GitHub Actions.

## Stack
- Java 21
- Spring Boot 3
- Spring Web / Data JPA / Validation / Actuator
- PostgreSQL
- JUnit 5 + Mockito + AssertJ
- WireMock
- Docker Compose

## Endpoints
### Buscar CEP
`GET /api/ceps/{cep}`

Exemplo:
```bash
curl http://localhost:8080/api/ceps/01001000
```

### Healthcheck
`GET /actuator/health`

## Estrutura principal
- `application`: caso de uso e DTOs
- `domain`: portas e entidade de domínio
- `infrastructure`: controller, adapter HTTP externo e repositório JPA

## Subindo local
1. Subir dependências:
```bash
docker compose up -d postgres wiremock
```
2. Executar aplicação:
```bash
mvn spring-boot:run
```

## WireMock (massa)
O mock principal está em:
- `wiremock/mappings/cep-01001000.json`

Você pode adicionar novos CEPs criando novos arquivos em `wiremock/mappings/*.json`.

## CI
Pipeline em:
- `.github/workflows/ci.yml`

Executa `mvn clean verify` com Java 21 e gera cobertura via JaCoCo.
