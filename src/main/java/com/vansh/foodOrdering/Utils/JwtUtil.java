package com.vansh.foodOrdering.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivilegedAction;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "this-is-my-secret-key-that-is-at-least-32-bytes-long";;
    private static final long EXPIRATION_TIME = 1000*60*60*24*7;
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());;



    public String generateToken(String email, Set<String> roles){
        return Jwts.builder()
                .setSubject(email)
                .claim("role", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, String userEmail){
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(userEmail) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        final Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
