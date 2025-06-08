package com.condominio.auth_service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void testConstructorAndGetter() {
        String token = "jwt.token.aqui";
        AuthResponse response = new AuthResponse(token);

        assertEquals(token, response.getToken());
    }

    @Test
    void testSetter() {
        AuthResponse response = new AuthResponse(null);
        String token = "novo.token";
        response.setToken(token);

        assertEquals(token, response.getToken());
    }

    @Test
    void testToStringNotNull() {
        AuthResponse response = new AuthResponse("token123");
        assertNotNull(response.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthResponse r1 = new AuthResponse("token");
        AuthResponse r2 = new AuthResponse("token");
        AuthResponse r3 = new AuthResponse("outroToken");

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
