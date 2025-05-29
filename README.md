# auth-service

🔧 Tecnologias

. Backend: Java 17 (Spring Boot)

. Banco de Dados: PostgreSQL

. Infraestrutura: AWS (EC2, S3, IAM)

. Segurança: JWT para autenticação e controle de acesso

📦 Módulos Funcionais
1. Autenticação & Autorização
JWT com roles: ROLE_PROPRIETARIO, ROLE_RESIDENTE, ROLE_ADMIN

2. Spring Security + filtros para autorização baseada em rota

3. Login e refresh token

4. endpoints
. Auth
   . POST: http://localhost:8080/api/auth/register
  body json
   ```
   {
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "senha": "12345678"
}
  
   ```
   
. Login
   . POST: http://localhost:8080/api/auth/login

```
{
  "email": "joao.silva@email.com",
  "senha": "12345678"
}
```
  
