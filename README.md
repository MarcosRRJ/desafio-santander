# Case Técnico — Consulta de CEP (Java + Spring Boot)

Este repositório foi preparado para o case da vaga Java (Santander/NTT DATA), com foco em:

- Consulta de CEP em API externa (mockável com WireMock)
- Persistência de logs de consulta com data/hora e payload retornado
- Uso de SOLID e separação por camadas
- Entrega apresentável em até 15 minutos

## 1) Escopo do desafio

A aplicação deve disponibilizar endpoint para busca de CEP e registrar todo histórico de consultas.

### Requisitos atendidos no desenho proposto

1. **Desenho de solução** → arquivo `docs/arquitetura.md`.
2. **Busca de CEP em API externa** → via provider HTTP desacoplado (permitindo WireMock/Mockoon).
3. **Logs em banco** → tabela/coleção `cep_query_log` com timestamp e conteúdo retornado.
4. **SOLID** → portas/interfaces e implementação por responsabilidade única.
5. **Git público** → basta publicar este repositório.

## 2) Arquitetura sugerida

Consulte `docs/arquitetura.md` para diagrama e fluxo completo.

Resumo:

- **Controller** recebe requisição `/api/ceps/{cep}`
- **UseCase/Service** orquestra validação, consulta externa e gravação de log
- **External Client** chama API de CEP (ViaCEP ou mock WireMock)
- **Repository** persiste logs (PostgreSQL ou MongoDB)

## 3) Estrutura recomendada de pacotes (hexagonal simplificada)

```text
src/main/java/br/com/backend/victorr/cep
  ├── application
  │   ├── usecase
  │   └── dto
  ├── domain
  │   ├── model
  │   ├── port/in
  │   └── port/out
  ├── infrastructure
  │   ├── controller
  │   ├── persistence
  │   └── external
  └── config
```

## 4) Roteiro de implementação (3 a 5 dias)

### Dia 1
- Criar endpoint `GET /api/ceps/{cep}`
- Modelar DTO de resposta
- Criar client HTTP para API externa

### Dia 2
- Criar entidade de log (`cep`, `timestamp`, `status`, `payload`)
- Persistir em banco
- Tratar erros (CEP inválido, timeout, API indisponível)

### Dia 3
- Testes unitários com JUnit + Mockito + AssertJ
- Teste de integração com WireMock
- Docker Compose (app + db + wiremock)

### Dia 4/5 (diferenciais)
- Actuator + logs estruturados
- Métricas para observabilidade
- Pipeline básico no Azure DevOps/GitHub Actions

## 5) Endpoints esperados

### Buscar CEP
```http
GET /api/ceps/01001000
```

**Resposta (200):**
```json
{
  "cep": "01001-000",
  "logradouro": "Praça da Sé",
  "bairro": "Sé",
  "localidade": "São Paulo",
  "uf": "SP"
}
```

### Consultar histórico
```http
GET /api/ceps/logs?cep=01001000
```

## 6) Perguntas que vão cair na apresentação

- Como você garantiu desacoplamento da API externa?
- Onde aplicou SOLID (SRP, DIP, OCP)?
- Como simular indisponibilidade da API de CEP?
- Como garantir rastreabilidade das consultas?
- Como evoluir para arquitetura orientada a eventos?

## 7) Script de apresentação (15 min)

1. **Problema e requisitos (2 min)**
2. **Desenho de solução (4 min)**
3. **Código (4 min)**
4. **Subida da aplicação + chamada de endpoint (3 min)**
5. **Perguntas e próximos passos (2 min)**

## 8) Resposta pronta para recrutadora

Template em `docs/resposta-recrutadora.md`.

---

Se você quiser, no próximo passo eu já implemento a API de CEP completa em cima deste projeto com:
- Java 21 + Spring Boot 3
- PostgreSQL
- WireMock
- Testes unitários e integração
- Docker Compose
