package com.app.veterinaria.service;

import com.app.veterinaria.dto.UserLoginRequest;
import com.app.veterinaria.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {

    final private AuthenticationManager authenticationManager;
    final private JwtUtils jwtUtils;

    public ResponseEntity<?> login(UserLoginRequest userLoginRequest) {

        var response = new LinkedHashMap<String, Object>();

        var username = userLoginRequest.getUsername();
        var password = userLoginRequest.getPassword();

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        authentication = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        var token = jwtUtils.generateToken(userDetails);
        var role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
                .getFirst();

        response.put("status", HttpStatus.ACCEPTED);
        response.put("username", username);
        response.put("role", role);
        response.put("token", token);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public String getUserRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
                .getFirst();
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
