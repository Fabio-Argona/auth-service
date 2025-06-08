package com.condominio.auth_service.dto;

import com.condominio.auth_service.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    @Test
    void testGettersAndSetters() {
        RegisterRequest request = new RegisterRequest();

        request.setNome("João Silva");
        request.setEmail("joao@example.com");
        request.setSenha("123456");
        request.setRole(Role.ROLE_PROPRIETARIO); // supondo que Role.USER exista

        assertEquals("João Silva", request.getNome());
        assertEquals("joao@example.com", request.getEmail());
        assertEquals("123456", request.getSenha());
        assertEquals(Role.ROLE_PROPRIETARIO, request.getRole());
    }
}
