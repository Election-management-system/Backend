package com.backend.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // 🔐 MUST be at least 32 chars (256 bits)
    private static final String SECRET =
            "smartvote-secret-key-smartvote-2026-secure";

    private static final long EXPIRATION =
            1 * 60 * 60 * 1000; // 24 hours

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ Generate Token
    public String generateToken(UserDetails user) {

        String role = user.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(key)   // ✅ NO SignatureAlgorithm needed
                .compact();
    }

    // ✅ Extract username (email)
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
