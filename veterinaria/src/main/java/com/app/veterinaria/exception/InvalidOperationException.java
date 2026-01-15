package com.app.veterinaria.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class InvalidOperationException extends RuntimeException {

    final private HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    private HttpMethod httpMethod = HttpMethod.POST;
    private String path;
    final private LocalDateTime timestamp;

    public InvalidOperationException(String message) {
        super(message);
        this.timestamp = LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
    }

    public InvalidOperationException(String message, String path) {
        super(message);
        this.path = path;
        this.timestamp = LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
    }
}
