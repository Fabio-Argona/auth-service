package com.condominio.auth_service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void testGettersAndSetters() {
        AuthRequest dto = new AuthRequest();

        dto.setEmail("teste@exemplo.com");
        dto.setSenha("123456");
        dto.setRole("ROLE_USER");

        assertEquals("teste@exemplo.com", dto.getEmail());
        assertEquals("123456", dto.getSenha());
        assertEquals("ROLE_USER", dto.getRole());
    }

    @Test
    void testToStringNotNull() {
        AuthRequest dto = new AuthRequest();
        dto.setEmail("teste@exemplo.com");
        dto.setSenha("123456");
        dto.setRole("ROLE_ADMIN");

        assertNotNull(dto.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthRequest dto1 = new AuthRequest();
        dto1.setEmail("a@a.com");
        dto1.setSenha("123");
        dto1.setRole("ROLE_USER");

        AuthRequest dto2 = new AuthRequest();
        dto2.setEmail("a@a.com");
        dto2.setSenha("123");
        dto2.setRole("ROLE_USER");

        AuthRequest dto3 = new AuthRequest();
        dto3.setEmail("b@b.com");
        dto3.setSenha("321");
        dto3.setRole("ROLE_ADMIN");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
