package com.condominio.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AutenticacaoException.class)
    public ResponseEntity<String> handleAutenticacaoException(AutenticacaoException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()); // Retorna 401
    }
}
