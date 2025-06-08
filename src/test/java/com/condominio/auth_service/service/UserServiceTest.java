package com.condominio.auth_service.service;

import com.condominio.auth_service.entity.Usuario;
import com.condominio.auth_service.enums.Role;
import com.condominio.auth_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // Usa o application-test.properties
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        Usuario user = Usuario.builder()
                .nome("Teste")
                .email("teste@teste.com")
                .senha(new BCryptPasswordEncoder().encode("123456"))
                .role(Role.ROLE_PROPRIETARIO)
                .build();
        usuarioRepository.save(user);
    }

    @Test
    void loadUserByUsername_ShouldReturnUser_WhenUserExists() {
        UserDetails userDetails = userService.loadUserByUsername("teste@teste.com");
        assertNotNull(userDetails);
        assertEquals("teste@teste.com", userDetails.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches("123456", userDetails.getPassword()));
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        assertThrows(
                org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("naoexiste@teste.com")
        );
    }
}
