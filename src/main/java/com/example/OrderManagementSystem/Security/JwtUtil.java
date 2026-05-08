package com.example.OrderManagementSystem.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor( "mysecretkeymysecretkeymysecretkey12"
            .getBytes());
    public static String generateToken(String email) {
        return Jwts.builder().subject(email)
                .issuedAt(new Date())
                .expiration( new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY).compact();
    }
    public static String extractEmail(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}
