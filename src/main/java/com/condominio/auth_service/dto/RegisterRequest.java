package com.condominio.auth_service.dto;

import lombok.Data;

import com.condominio.auth_service.enums.Role;

@Data
public class RegisterRequest {
    private String nome;
    private String email;
    private String senha;
    private Role role;
}

