package com.condominio.auth_service.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String senha;
    private String role;
}
