package com.app.veterinaria.controller;

import com.app.veterinaria.dto.NewVeterinarianRequest;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.service.UserService;
import com.app.veterinaria.service.VeterinarianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

/*
    CLASE DE ACCESO UNICO MEDIANTE ROL DE ADMIN
*/

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    final private UserService userService;
    final private VeterinarianService veterinarianService;

    @PostMapping("/veterinarians")
    public ResponseEntity<?> createVeterinario(@Valid @RequestBody NewVeterinarianRequest request) {
        return veterinarianService.createAndSaveVeterinarianWithResponse(request);
    }


    @GetMapping("/me")
    public ResponseEntity<?> findByUsername(Authentication authentication) {
        String username = authentication.getName();
        var user = userService.findByUsername(username);
        var body = new LinkedHashMap<String, Object>();
        body.put("username", user.getUsername());
        body.put("email", user.getEmail());
        body.put("role", user.getRole().getAuthority());
        body.put("createdAt", user.getCreatedAt());

        return ResponseEntity.ok(body);
    }


}
