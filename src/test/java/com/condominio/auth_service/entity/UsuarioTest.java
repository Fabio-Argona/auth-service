package com.condominio.auth_service.entity;

import com.condominio.auth_service.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testUsuarioEntityAndUserDetailsMethods() {
        Usuario usuario = Usuario.builder()
                .id(10L)
                .nome("João")
                .email("joao@example.com")
                .senha("123456")
                .role(Role.ROLE_PROPRIETARIO)
                .build();

        // Testa getters
        assertEquals(10L, usuario.getId());
        assertEquals("João", usuario.getNome());
        assertEquals("joao@example.com", usuario.getEmail());
        assertEquals("123456", usuario.getSenha());
        assertEquals(Role.ROLE_PROPRIETARIO, usuario.getRole());

        // Testa métodos do UserDetails
        assertEquals("123456", usuario.getPassword());
        assertEquals("joao@example.com", usuario.getUsername());

        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_PROPRIETARIO", authorities.iterator().next().getAuthority());

        assertTrue(usuario.isAccountNonExpired());
        assertTrue(usuario.isAccountNonLocked());
        assertTrue(usuario.isCredentialsNonExpired());
        assertTrue(usuario.isEnabled());
    }
}
