package com.condominio.auth_service.exception;

public class AutenticacaoException extends RuntimeException {
    public AutenticacaoException(String mensagem) {
        super(mensagem);
    }
}
