package com.app.veterinaria.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class DuplicateResourceException extends RuntimeException {
    final private HttpStatus status = HttpStatus.CONFLICT;
    final private LocalDateTime timestamp = LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
    private String path;
    private HttpMethod method = HttpMethod.POST;

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, String path) {
        super(message);
        this.path = path;
    }
}
