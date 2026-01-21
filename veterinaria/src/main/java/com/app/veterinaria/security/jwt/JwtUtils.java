package com.app.veterinaria.security.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    // extraer token del header de la request
    public String extractJwt(HttpServletRequest request) {
        var bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // generar token
    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        var roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var claims = new HashMap<String, Object>();
        claims.put("role", roles.getFirst());

        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(getSign())
                .compact();
    }

    // obtener usuario desde un token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSign())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // obtener rol desde un token
    public Object getRole(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSign())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role");
    }

    // validar token
    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith((SecretKey) getSign())
                .build()
                .parseSignedClaims(token);
    }

    // obtener firma
    private Key getSign() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
