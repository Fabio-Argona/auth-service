package com.condominio.auth_service.security;

import com.condominio.auth_service.entity.Usuario;
import com.condominio.auth_service.enums.Role;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private JwtProperties jwtProperties;

    @BeforeEach
    void setup() {
        jwtProperties = Mockito.mock(JwtProperties.class);
        // Chave secreta base64 (256 bits)
        Mockito.when(jwtProperties.getSecret()).thenReturn("YmFzZTY0ZW5jb2RlZHNlY3JldGtleTEyMzQ1Njc4OTA=");
        Mockito.when(jwtProperties.getExpiration()).thenReturn(1000 * 60 * 60L); // 1 hora

        jwtService = new JwtService(jwtProperties);
    }

    @Test
    void testGenerateTokenAndValidate() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("Teste")
                .email("teste@teste.com")
                .senha("senha")
                .role(Role.ROLE_ADMIN)
                .build();

        String token = jwtService.generateToken(usuario);
        assertNotNull(token);

        // Extrai username do token
        String username = jwtService.extractUsername(token);
        assertEquals("teste@teste.com", username);

        // Valida token com usuário correto
        assertTrue(jwtService.isTokenValid(token, usuario));

        // Valida token com usuário diferente
        Usuario outroUsuario = Usuario.builder().email("outro@teste.com").build();
        assertFalse(jwtService.isTokenValid(token, outroUsuario));

        // Testa extração de claims (role)
        Claims claims = jwtService.getAllClaims(token);
        assertEquals("ROLE_ADMIN", claims.get("role"));
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        Mockito.when(jwtProperties.getExpiration()).thenReturn(1000L);
        jwtService = new JwtService(jwtProperties);

        Usuario usuario = Usuario.builder()
                .email("teste@teste.com")
                .role(Role.ROLE_PROPRIETARIO)
                .senha("senha")
                .build();

        String token = jwtService.generateToken(usuario);
        assertNotNull(token);

        // Token válido imediatamente após criação
        assertTrue(jwtService.isTokenValid(token, usuario));

        Thread.sleep(1500);

        // Após expiração, isTokenValid deve lidar com exceção e retornar false
        boolean isValid;
        try {
            isValid = jwtService.isTokenValid(token, usuario);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            isValid = false; // token expirado => inválido
        }

        assertFalse(isValid);
    }


}
