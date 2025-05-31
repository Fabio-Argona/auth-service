package com.condominio.auth_service.service;

import com.condominio.auth_service.dto.AuthRequest;
import com.condominio.auth_service.dto.AuthResponse;
import com.condominio.auth_service.dto.RegisterRequest;
import com.condominio.auth_service.dto.UserResponse;
import com.condominio.auth_service.entity.Usuario;
import com.condominio.auth_service.repository.UsuarioRepository;
import com.condominio.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        Usuario user = new Usuario();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setSenha(passwordEncoder.encode(request.getSenha()));
        user.setRole(request.getRole());

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        // Tenta autenticar com o AuthenticationManager usando email e senha (cru)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        // Se não lançar exceção, busca o usuário no banco
        Usuario user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gera o token JWT
        String token = jwtService.generateToken(user);

        // Retorna o token
        return new AuthResponse(token);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getNome(), user.getEmail(), user.getRole().name()))
                .collect(Collectors.toList());
    }

    public Usuario editarUsuario(Long id, RegisterRequest request) {
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        if (request.getSenha() != null && !request.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        }
        usuario.setRole(request.getRole());

        return userRepository.save(usuario);
    }

    public void excluirUsuario(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }
        userRepository.deleteById(id);
    }

}


