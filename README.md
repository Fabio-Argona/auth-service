# 🔐 API de Autenticação JWT - Auth Service

API REST para autenticação e autorização usando JWT, construída com Spring Boot 3.5 e Java 21.  
Permite cadastro, login e controle de acesso baseado em roles.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia              | Versão       |
|------------------------|--------------|
| 🟩 Java                | 21           |
| 🌱 Spring Boot         | 3.5          |
| 🔒 Spring Security      | JWT          |
| 📦 Maven               | Última       |
| 📚 Swagger / OpenAPI   | springdoc-openapi-starter-webmvc-ui 2.8.8 |
| 🐘 Banco de Dados       | PostgreSQL*  |
| 🐳 Docker              | Opcional     |

---

## 📡 Endpoints
### Método	Endpoint	Descrição	Acesso
.POST	/api/auth/register	Registrar novo usuário	Público
.POST	/api/auth/login	Login e retorno do token JWT	Público
.GET	/api/auth/user	Obter dados do usuário logado	Usuário autenticado
.POST	/api/auth/logout	Logout (opcional)	Usuário autenticado

