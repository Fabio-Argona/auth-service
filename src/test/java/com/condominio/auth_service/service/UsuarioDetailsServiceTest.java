package com.condominio.auth_service.service;

import com.condominio.auth_service.entity.Usuario;
import com.condominio.auth_service.exception.AutenticacaoException;
import com.condominio.auth_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioDetailsService usuarioDetailsService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setSenha("senhaCodificada");
        usuario.setNome("Usuário Teste");
        // configure outros atributos se necessário
    }

    @Test
    void loadUserByUsername_Sucesso() {
        when(usuarioRepository.findByEmail("teste@exemplo.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = (Usuario) usuarioDetailsService.loadUserByUsername("teste@exemplo.com");

        assertNotNull(resultado);
        assertEquals("teste@exemplo.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findByEmail("teste@exemplo.com");
    }

    @Test
    void loadUserByUsername_NaoEncontrado() {
        when(usuarioRepository.findByEmail("nao@existe.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                usuarioDetailsService.loadUserByUsername("nao@existe.com")
        );

        verify(usuarioRepository, times(1)).findByEmail("nao@existe.com");
    }




    @Test
    void autenticar_UsuarioNaoEncontrado() {
        when(usuarioRepository.findByEmail("nao@existe.com")).thenReturn(Optional.empty());

        AutenticacaoException exception = assertThrows(AutenticacaoException.class, () ->
                usuarioDetailsService.autenticar("nao@existe.com", "qualquerSenha")
        );

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(usuarioRepository, times(1)).findByEmail("nao@existe.com");
    }

}
