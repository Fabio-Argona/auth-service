# Auth Service - Sistema de Autenticação com JWT

Este projeto é responsável pela autenticação de usuários utilizando JWT e Spring Security. Ele integra-se com outros microsserviços e fornece endpoints seguros para login e cadastro de usuários, protegendo os recursos com controle de acesso baseado em roles (`ROLE_ADMIN`, `ROLE_PROPRIETARIO`, `ROLE_RESIDENTE`).

---

## 🧰 Tecnologias Utilizadas

- ☕ Java 21 JDK  
- 🗂 Spring Boot 3.2+
- 🔑 Spring Security
- 🔐 JWT (JSON Web Token)
- 🐘 PostgreSQL
- 📦 Maven 3.9+ 
- 📚 Swagger/OpenAPI
- 🐳 Docker e Docker Compose instalados
- 🔌 Porta 5432 livre para o PostgreSQL  
- 🔌 Porta 8081 livre para o serviço 

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

## 🛠️ Build do Projeto

Para gerar o arquivo `.jar` do projeto, execute:

```bash
mvn clean package -DskipTests
```

**Explicação:**

- 🧹 `clean`: limpa arquivos de builds anteriores  
- 📦 `package`: compila e empacota o projeto em `.jar`  
- ⏩ `-DskipTests`: pula execução dos testes para acelerar o build  

---

## 🐋 Dockerfile

O projeto utiliza multi-stage build para criar a imagem Docker:

```dockerfile
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
EXPOSE 8081
CMD ["java", "-jar", "auth-service.jar"]
```

---

## 📦 docker-compose.yml

```yaml
version: "3.8"

services:
  auth_db:
    image: postgres:15
    environment:
      POSTGRES_DB: auth_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - auth_db_data:/var/lib/postgresql/data

  auth_service:
    build: .
    depends_on:
      - auth_db
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://auth_db:5432/auth_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres

volumes:
  auth_db_data:
```

---

## 🔧 Variáveis de Ambiente

Para o container do serviço (`auth_service`), configure as seguintes variáveis:

- 🔗 `SPRING_DATASOURCE_URL`: URL de conexão com o banco (ex: `jdbc:postgresql://auth_db:5432/auth_db`)  
- 👤 `SPRING_DATASOURCE_USERNAME`: usuário do banco (ex: `postgres`)  
- 🔒 `SPRING_DATASOURCE_PASSWORD`: senha do banco (ex: `postgres`)  

---

## ▶️ Executando o Projeto

1. 🔎 Certifique-se que as portas 5432 e 8081 estão livres.  
2. 📂 No diretório do projeto, execute:

```bash
docker-compose up --build
```

3. ⏳ Aguarde o download das imagens, build do projeto e inicialização dos containers.  
4. 🌐 O serviço estará disponível em: [http://localhost:8081](http://localhost:8081)  
5. 🐘 O banco PostgreSQL estará disponível na porta 5432.  

---

## 🧪 Testando a API

- 📖 Você pode acessar a documentação Swagger na URL:

```
http://localhost:8081/swagger-ui/index.html
```

- 🚀 Use as rotas documentadas para testar os endpoints.  

---

## 💡 Dicas

- 🔄 Se quiser rodar o projeto localmente sem Docker, configure o arquivo `application.properties` para conectar-se ao banco PostgreSQL local.  
- 🛠️ Para rebuildar o projeto sem rodar os testes, utilize `mvn clean package -DskipTests`.  
- ✅ Para rodar os testes, execute `mvn test`.  

---



## 👨‍💻 Autor

Desenvolvido por Fabio Argona - Projeto de microsserviços para controle de condomínio .

---
  
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
=======
