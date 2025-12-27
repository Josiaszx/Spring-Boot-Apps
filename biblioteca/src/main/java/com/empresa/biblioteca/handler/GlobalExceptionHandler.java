package com.empresa.biblioteca.handler;

import com.empresa.biblioteca.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // para manejar errores en @RequestParam
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // para manejar errores en operaciones
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<?> handler(InvalidOperationException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // para manejar errores al buscar elementos inexistentes
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handler(NoSuchElementException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception ex) {
        return new ResponseEntity<>("some error happened", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handler(RuntimeException ex) {
        return new ResponseEntity<>("some error happened", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
