package com.condominio.auth_service.service;

import com.condominio.auth_service.dto.AuthRequest;
import com.condominio.auth_service.dto.AuthResponse;
import com.condominio.auth_service.entity.Usuario;
import com.condominio.auth_service.exception.AutenticacaoException;
import com.condominio.auth_service.repository.UsuarioRepository;
import com.condominio.auth_service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_validCredentials_returnsToken() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setEmail("user@example.com");
        request.setSenha("password");

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setSenha("encodedPassword");

        String fakeToken = "fake.jwt.token";

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(usuario)).thenReturn(fakeToken);

        // Simula autenticação sem exceção
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        // Act
        AuthResponse response = authService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertEquals(fakeToken, response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(request.getEmail());
        verify(jwtService).generateToken(usuario);
    }

    @Test
    void authenticate_invalidCredentials_throwsException() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setEmail("user@example.com");
        request.setSenha("wrongPassword");

        // Simula exceção ao autenticar
        doThrow(new AuthenticationException("Credenciais inválidas") {}).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        assertThrows(AuthenticationException.class, () -> authService.authenticate(request));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByEmail(anyString());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void authenticate_userNotFound_throwsAutenticacaoException() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setEmail("nonexistent@example.com");
        request.setSenha("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        AutenticacaoException ex = assertThrows(AutenticacaoException.class, () -> authService.authenticate(request));
        assertEquals("Usuário não encontrado. Verifique o e-mail e tente novamente.", ex.getMessage());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(request.getEmail());
        verify(jwtService, never()).generateToken(any());
    }

}
