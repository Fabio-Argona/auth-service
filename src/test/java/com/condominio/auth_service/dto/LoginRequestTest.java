package com.condominio.auth_service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void testGettersAndSetters() {
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername("usuario123");
        loginRequest.setPassword("senhaSegura");

        assertEquals("usuario123", loginRequest.getUsername());
        assertEquals("senhaSegura", loginRequest.getPassword());
    }

    @Test
    void testToStringNotNull() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("pass");
        assertNotNull(loginRequest.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        LoginRequest lr1 = new LoginRequest();
        lr1.setUsername("user");
        lr1.setPassword("pass");

        LoginRequest lr2 = new LoginRequest();
        lr2.setUsername("user");
        lr2.setPassword("pass");

        LoginRequest lr3 = new LoginRequest();
        lr3.setUsername("user2");
        lr3.setPassword("pass2");

        assertEquals(lr1, lr2);
        assertNotEquals(lr1, lr3);
        assertEquals(lr1.hashCode(), lr2.hashCode());
    }
}
