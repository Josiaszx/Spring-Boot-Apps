package com.app.veterinaria.service;

import com.app.veterinaria.dto.NewUserRequest;
import com.app.veterinaria.entity.Role;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.security.UserLoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    final private UserService userService;
    final private RoleService roleService;

    public static ResponseEntity<?> login(UserLoginRequest userLoginRequest) {
        // todo: implemtear jwt
        return null;
    }

    public User register(@Valid NewUserRequest user) {
        return userService.createAndSaveUserFrom(user);
    }
}
