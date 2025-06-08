package com.condominio.auth_service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        UserResponse user = new UserResponse();

        user.setId(1L);
        user.setNome("Maria");
        user.setEmail("maria@example.com");
        user.setRole("ROLE_ADMIN");

        assertEquals(1L, user.getId());
        assertEquals("Maria", user.getNome());
        assertEquals("maria@example.com", user.getEmail());
        assertEquals("ROLE_ADMIN", user.getRole());
    }

    @Test
    void testAllArgsConstructor() {
        UserResponse user = new UserResponse(2L, "Pedro", "pedro@example.com", "USER");

        assertEquals(2L, user.getId());
        assertEquals("Pedro", user.getNome());
        assertEquals("pedro@example.com", user.getEmail());
        assertEquals("USER", user.getRole());
    }
}
