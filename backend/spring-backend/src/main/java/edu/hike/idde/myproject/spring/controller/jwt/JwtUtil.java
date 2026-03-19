package edu.hike.idde.myproject.spring.controller.jwt;

import edu.hike.idde.myproject.spring.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static String secretKey = "my-super-secret-and-very-long-key-for-jwt-signing-256bits";

    public String generateToken(User user) {
        long expirationMs = 1000 * 60 * 60;
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public Long getUserId(String token) {
        return Long.parseLong(validateToken(token).getSubject());
    }

    public String getRole(String token) {
        return validateToken(token).get("role", String.class);
    }
}
