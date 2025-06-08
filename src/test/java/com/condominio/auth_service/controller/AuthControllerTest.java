package com.condominio.auth_service.controller;

import com.condominio.auth_service.dto.AuthRequest;
import com.condominio.auth_service.dto.AuthResponse;
import com.condominio.auth_service.dto.RegisterRequest;
import com.condominio.auth_service.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Given
        RegisterRequest request = new RegisterRequest();
        // preencher campos do request se desejar
        AuthResponse expectedResponse = new AuthResponse("token123");
        when(authService.register(request)).thenReturn(expectedResponse);

        // When
        ResponseEntity<AuthResponse> responseEntity = authController.register(request);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("token123", responseEntity.getBody().getToken());
        verify(authService, times(1)).register(request);
    }

    @Test
    void testLogin() {
        // Given
        AuthRequest request = new AuthRequest();
        // preencher campos do request se desejar
        AuthResponse expectedResponse = new AuthResponse("token456");
        when(authService.authenticate(request)).thenReturn(expectedResponse);

        // When
        ResponseEntity<AuthResponse> responseEntity = authController.login(request);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("token456", responseEntity.getBody().getToken());
        verify(authService, times(1)).authenticate(request);
    }
}

