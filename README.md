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

```colections insomnia
type: collection.insomnia.rest/5.0
name: Condominio
meta:
  id: wrk_78f0bb08acaf4ee7b6c7cb4bcf92e0e9
  created: 1748548411401
  modified: 1748558739083
  description: ""
collection:
  - name: auth-service
    meta:
      id: fld_d1faa0d8eac94a51b2028b6221e164aa
      created: 1748553276594
      modified: 1749095616667
      sortKey: -1748564115461
      description: ""
    children:
      - url: http://localhost:8081/api/auth/login
        name: 2- Autenticar
        meta:
          id: req_b992fa21344a4466a156d836264e4d95
          created: 1748548411462
          modified: 1749344379114
          isPrivate: false
          description: ""
          sortKey: -1748553285662
        method: POST
        body:
          mimeType: application/json
          text: |+
            {
              "email": "paty@condominio.com",
              "senha": "proprietario12"
            }

        headers:
          - name: Content-Type
            value: application/json
            id: pair_5cff7ff7fc114c3aa236d5fb2ef3df9b
          - name: User-Agent
            value: insomnia/11.1.0
            id: pair_f64d678371874c41bf93a23e4ed75d67
          - id: pair_052c4e4fe4484145a79bd8e8f9223de3
            name: ""
            value: ""
            description: ""
            disabled: true
        authentication:
          type: apikey
          disabled: false
          key: ""
          value: ""
          addTo: header
        settings:
          renderRequestBody: true
          encodeUrl: true
          followRedirects: global
          cookies:
            send: true
            store: true
          rebuildPath: true
      - url: http://localhost:8081/api/auth/register
        name: 1- Registrar Usuario
        meta:
          id: req_5a0e2e320c14430ea9b329766a590372
          created: 1748548464031
          modified: 1749344415646
          isPrivate: false
          description: ""
          sortKey: -1748553285562
        method: POST
        body:
          mimeType: application/json
          text: |
            {
              "nome": "fabio Adim",
              "email": "domaer@condominio.com",
              "senha": "residente123",
              "role": "ROLE_RESIDENTE"
            }
        parameters:
          - id: pair_4ae34662bf3541c1bfae570b165627e6
            name: ""
            value: ""
            description: ""
            disabled: false
          - id: pair_7e1a4f1c0f51474ba366577d7aad21bc
            name: ""
            value: ""
            description: ""
            disabled: false
        headers:
          - name: Content-Type
            value: application/json
          - name: User-Agent
            value: insomnia/11.1.0
        settings:
          renderRequestBody: true
          encodeUrl: true
          followRedirects: global
          cookies:
            send: true
            store: true
          rebuildPath: true
  - name: Cadastro Pessoa
    meta:
      id: fld_250cc9b0c8be45899e4a7dca0fa193db
      created: 1748564115361
      modified: 1749245369897
      sortKey: -1748564115361
      description: ""
cookieJar:
  name: Default Jar
  meta:
    id: jar_c3cee3d922007268c20a7519278931ff5dcf697f
    created: 1748548411406
    modified: 1749256718559
  cookies:
    - key: JSESSIONID
      value: 630EF3A946A48EA0CA533542F2C36BA6
      domain: localhost
      path: /
      httpOnly: true
      hostOnly: true
      creation: 2025-05-29T23:21:26.539Z
      lastAccessed: 2025-06-07T00:38:38.559Z
      id: abf425d3-f288-4dcc-88ab-1e8c7fb80acd
environments:
  name: Base Environment
  meta:
    id: env_c3cee3d922007268c20a7519278931ff5dcf697f
    created: 1748548411405
    modified: 1749264261280
    isPrivate: false
  data:
    '"jwt_token":""': eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsInN1YiI6ImFkbWluQGVtYWlsLmNvbSIsImlhdCI6MTc0OTI2NDIzOCwiZXhwIjoxNzQ5MzUwNjM4fQ.Cz_8hjddHUu19VTZ-sCcozccDHFAs2yqze7voHE0MSw
    '"base_url":""': "http://localhost:"


```

## 👨‍💻 Autor

Desenvolvido por Fabio Argona - Projeto de microsserviços para controle de condomínio .

---
  
