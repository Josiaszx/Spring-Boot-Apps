package com.app.veterinaria.controller;

import com.app.veterinaria.dto.NewUserRequest;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.security.UserLoginRequest;
import com.app.veterinaria.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    final private AuthenticationService authenticationService;

    // TODO: inyectar objeto Authentication
    @PostMapping("/register")
    public User register(@Valid @RequestBody NewUserRequest user) {
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
        return AuthenticationService.login(userLoginRequest);
    }
}
