package com.app.veterinaria.controller;

import com.app.veterinaria.dto.NewVeterinarianRequest;
import com.app.veterinaria.service.UserService;
import com.app.veterinaria.service.VeterinarianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
