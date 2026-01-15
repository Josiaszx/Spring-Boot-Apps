package com.app.veterinaria.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    final private HttpStatus STATUS_CODE = HttpStatus.NOT_FOUND;
    final private String STATUS_MESSAGE = HttpStatus.NOT_FOUND.getReasonPhrase();
    private String path;
    private HttpMethod method = HttpMethod.GET;
    private LocalDateTime timestamp;

    public ResourceNotFoundException(String message) {
        super(message);
        timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public ResourceNotFoundException(String message, String path, HttpMethod method) {
        super(message);
        this.path = path;
        this.method = method;
        timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

}
