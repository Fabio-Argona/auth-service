package com.condominio.auth_service.service;

import com.condominio.auth_service.entity.Usuario;
import com.condominio.auth_service.exception.AutenticacaoException;
import com.condominio.auth_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        return usuario;
    }

    public void autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new AutenticacaoException("Usuário não encontrado."));

        if (!validarSenha(senha, usuario.getSenha())) {
            throw new AutenticacaoException("Senha incorreta.");
        }
    }

    private boolean validarSenha(String senhaInformada, String senhaArmazenada) {
        return senhaInformada.equals(senhaArmazenada);
    }
}
