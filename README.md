# Auth Service - Sistema de Autenticação com JWT

Este projeto é responsável pela autenticação de usuários utilizando JWT e Spring Security. Ele integra-se com outros microsserviços e fornece endpoints seguros para login e cadastro de usuários, protegendo os recursos com controle de acesso baseado em roles (`ROLE_ADMIN`, `ROLE_PROPRIETARIO`, `ROLE_RESIDENTE`).

---

## 🧰 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2+
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Maven
- Swagger/OpenAPI
- Docker

---

## ⚙️ Como Executar o Projeto

### 1. Subir o PostgreSQL com Docker

```bash
docker run --name auth-postgres   -e POSTGRES_USER=usuario   -e POSTGRES_PASSWORD=senha   -e POSTGRES_DB=authdb   -p 5432:5432   -d postgres
```

### 2. Configurar `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/authdb
spring.datasource.username=usuario
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update

# JWT
auth.jwt.secret=chaveSecretaJWT
auth.jwt.expiration=3600000

# Swagger
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

---

### 3. Executar a Aplicação

Via Maven:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

A aplicação será executada em: `http://localhost:8081`.

---

## 📖 Documentação da API

Acesse a documentação gerada automaticamente via Swagger:

🔗 [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

![image](https://github.com/user-attachments/assets/6b85acf9-310c-44cf-8d24-3d5fb289f48b)


---

## 🔐 Autenticação JWT

A API gera tokens JWT que devem ser utilizados no header `Authorization` das requisições protegidas:

```
Authorization: Bearer <token>
```

---

## 🧪 Testes com Curl

### 🔍 Buscar usuário inexistente

```bash
curl -i -X GET "http://localhost:8081/api/usuarios?email=inexistente@teste.com"
```

### 🔐 Login com senha incorreta

```bash
curl -i -X POST "http://localhost:8081/api/auth/login"   -H "Content-Type: application/json"   -d '{"email":"usuario@teste.com", "senha":"senha_errada"}'
```

---

## 💡 Tratamento de Erros

A API conta com uma estrutura de exceções personalizada para fornecer mensagens claras e status HTTP apropriados:

| Exceção                     | Status | Mensagem                              |
|----------------------------|--------|---------------------------------------|
| `UsernameNotFoundException`| 404    | Usuário não encontrado                |
| `AutenticacaoException`    | 401    | Email ou senha inválidos              |
| `Exception` (genérica)     | 500    | Erro interno inesperado               |

---

## 📂 Estrutura de Pacotes

```
src/main/java/com/condominio/auth_service/
├── config                // Configurações do projeto (Swagger, CORS, etc.)
├── controller            // Endpoints REST
├── dto                   // Classes de entrada e saída (DTOs)
├── entity                // Entidades JPA
├── exception             // Exceções personalizadas e tratamento global
├── repository            // Interfaces JPA
├── security              // JWT, filtros, configuração Spring Security
├── service               // Regras de negócio e autenticação
└── AuthServiceApplication.java
```

---

## 📌 Endpoints Principais

| Método | Endpoint               | Descrição                     |
|--------|------------------------|-------------------------------|
| POST   | `/api/auth/register`   | Registra novo usuário         |
| POST   | `/api/auth/login`      | Autentica e retorna JWT       |
| GET    | `/api/usuarios`        | Busca usuário por email       |
| GET    | `/swagger-ui.html`     | Interface Swagger             |

---

## 👨‍💻 Autor

Desenvolvido por [Seu Nome Aqui] - Projeto de microsserviços para controle de condomínio.

---
  
