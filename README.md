# Sistema de Votação - Cooperativa

API REST para gerenciamento de pautas e sessões de votação em assembleias. 

## 🚀 Tecnologias Utilizadas

- **Java 21** & **Spring Boot 3.3.6**
- **Spring Data JPA** & **PostgreSQL** (Persistência)
- **Liquibase** (Migração de Banco de Dados)
- **Spring Kafka** (Mensageria para resultados)
- **Spring Cache + Caffeine** (Performance)
- **OpenFeign** (Consumo de API externa de CPF)
- **Springdoc OpenAPI (Swagger)** (Documentação)
- **Docker & Docker Compose** (Containerização)
- **JUnit 5 & Mockito** (Testes Automatizados)

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas inspirada em **DDD (Domain-Driven Design)** e **Clean Architecture**:

- **API Layer**: Controllers, DTOs e Global Exception Handler.
- **Domain Layer**: Entidades de negócio, Services, Interfaces de Repositório e Clientes.
- **Infrastructure Layer**: Configurações de mensageria, cache, persistência e migrações.

### Diferenciais Implementados:
- **Processamento Assíncrono**: Um Scheduler processa pautas fechadas e publica os resultados no Kafka.
- **Resiliência**: Tratamento de erros estruturado (RFC 7807) e validações consistentes.
- **Escalabilidade**: Uso de cache para contagem de votos e índices otimizados no banco de dados.
- **DX (Developer Experience)**: Documentação Swagger completa e ambiente Docker pronto para uso.

## 🛠️ Como Rodar o Projeto

### Pré-requisitos
- Docker e Docker Compose instalados.

### Execução Completa (App + DB + Kafka)
```bash
docker-compose up --build
```
A API estará disponível em `http://localhost:8080`.

### Acessando a Documentação (Swagger)
Acesse: `http://localhost:8080/swagger-ui.html`

## 🧪 Testes

### Executar Testes Automatizados
```bash
mvn test
```
*Os testes utilizam banco de dados H2 em memória para isolamento total.*

## 📋 Principais Endpoints

- `POST /v1/pautas`: Cadastra uma nova pauta.
- `POST /v1/pautas/{id}/abrir-sessao`: Abre uma sessão de votação (tempo padrão: 1 min).
- `POST /v1/pautas/{id}/votos`: Registra o voto de um associado (Valida CPF e duplicidade).
- `GET /v1/pautas/{id}/resultado`: Consulta o resultado parcial ou final.

## 🔗 Integrações Externas
- **Validação de CPF**: O sistema consome uma API externa via Feign Client para validar se o associado está habilitado para votar.
- **Kafka**: Os resultados das pautas são postados no tópico `votacao-resultados` automaticamente após o fechamento da sessão.

## ⚙️ CI/CD
O projeto conta com GitHub Actions configurado para execução automática de build e testes em cada Pull Request ou Push para a branch `main`.

---
Desenvolvido por **Silber Lima**
