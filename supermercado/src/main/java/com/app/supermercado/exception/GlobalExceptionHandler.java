package com.app.supermercado.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.View;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
@Order(1)
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> IllegalArgumentException(IllegalArgumentException ex) {
        var body = new HashMap<String, String>();
        body.put("mensaje", "Ocurrio un error al procesar la solicitud");
        body.put("status", HttpStatus.BAD_REQUEST.toString());
        body.put("error", ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var body = new HashMap<String, String>();
        body.put("mensaje", "El curpo de la solicitud es invalida");
        body.put("status", HttpStatus.BAD_REQUEST.toString());
        body.put("error", ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

}

