package com.condominio.auth_service.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    @Test
    void testGetToken() {
        String token = "meuToken123";
        LoginResponse response = new LoginResponse(token);

        assertEquals(token, response.getToken());
    }

    @Test
    void testConstructor() {
        LoginResponse response = new LoginResponse("tokenTeste");
        assertNotNull(response);
        assertEquals("tokenTeste", response.getToken());
    }
}
