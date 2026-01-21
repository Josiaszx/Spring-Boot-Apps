package com.app.veterinaria.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
          HttpServletRequest request,
          HttpServletResponse response,
          AuthenticationException authException
  ) throws IOException, ServletException {

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    var responseBody = new LinkedHashMap<String, Object>();
    responseBody.put("status", HttpStatus.UNAUTHORIZED);
    responseBody.put("error", "Unauthorized");
    responseBody.put("message", authException.getMessage());
    responseBody.put("path", request.getServletPath());
    responseBody.put("timestamp", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

    var objectMapper = new ObjectMapper();
    objectMapper.writeValue(response.getOutputStream(), responseBody);
  }
}
