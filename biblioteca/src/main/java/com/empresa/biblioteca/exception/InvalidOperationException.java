package com.empresa.biblioteca.exception;

// error para cuando los datos esperados son incorrectos
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
