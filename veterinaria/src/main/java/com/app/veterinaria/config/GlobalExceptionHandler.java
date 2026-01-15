package com.app.veterinaria.config;

import com.app.veterinaria.exception.DuplicateResourceException;
import com.app.veterinaria.exception.ResourceNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        var response = generateResponse(ex.getTimestamp(), ex.getSTATUS_CODE(), ex.getMessage(), ex.getPath(), ex.getMethod());
        return ResponseEntity.status(ex.getSTATUS_CODE()).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        var body = generateResponse(
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), BAD_REQUEST,
                BAD_REQUEST.getReasonPhrase(), ex.getMessage(), HttpMethod.GET
        );
        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDuplicateResourceException(DuplicateResourceException ex) {
        var body = generateResponse(ex.getTimestamp(), ex.getStatus(), ex.getMessage(), ex.getPath(), ex.getMethod());
        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    private LinkedHashMap<String, Object> generateResponse(LocalDateTime timestamp, HttpStatus status, String message, String path, HttpMethod method) {
        var response = new LinkedHashMap<String, Object>();
        response.put("timestamp", timestamp);
        response.put("status Code", status);
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("path", path);
        response.put("method", method.name());
        return response;
    }
}
