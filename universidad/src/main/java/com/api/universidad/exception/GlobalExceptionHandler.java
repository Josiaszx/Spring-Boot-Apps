package com.api.universidad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "Not Found", request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> businessException(BusinessException ex, HttpServletRequest request){
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Business Error", request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status, String error, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, status);
    }
}
