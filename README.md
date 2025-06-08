# Auth Service

API de autenticação com JWT desenvolvida em Spring Boot, PostgreSQL, Docker e Flyway.

## 🐳 Subindo com Docker

```bash
docker-compose up --build
```

A aplicação ficará disponível em: http://localhost:8081  
Swagger: http://localhost:8081/swagger-ui/index.html

## 📦 Build do JAR

```bash
./mvnw clean package
```

## 🧪 Testando localmente (sem Docker)

- PostgreSQL rodando localmente na porta 5432
- Banco: `auth_db`
- Usuário: `postgres`
- Senha: `postgres`

## 📁 Estrutura

- `Dockerfile` — Build do container da aplicação
- `docker-compose.yml` — Subida do banco e do serviço
- `application.properties` — Configurações Spring Boot

## 🔐 Endpoints principais

| Método | Endpoint               | Autenticação |
|--------|------------------------|--------------|
| POST   | `/auth/register`       | ❌           |
| POST   | `/auth/login`          | ❌           |
| GET    | `/usuarios`            | ✅ JWT       |