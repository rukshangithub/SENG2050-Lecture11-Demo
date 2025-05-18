package com.example.lecture11demo.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtUtil {

    // Reading configuration values
    @Value("${spring.jwt.secret}")
    private String secretKeyValue;

    @Value("${spring.jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${spring.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    // Declaring static variables
    private static String SECRET_KEY;
    private static long ACCESS_EXPIRATION_MS; 
    private static long REFRESH_EXPIRATION_MS;

    @PostConstruct
    public void init()
    {
        // Initialising static variables
        SECRET_KEY = secretKeyValue;
        ACCESS_EXPIRATION_MS = accessTokenExpiration;
        REFRESH_EXPIRATION_MS = refreshTokenExpiration;
    }

  
    // // NOTE: Demo purposes only
    // If refresh tokens are stored in server side, they need to be stored securely
    // (e.g. in a database). This allows the server to manage and revoke tokens as needed. 
    private static final Map<String, String> refreshTokens = new ConcurrentHashMap<>();

    public static String generateAccessToken(String username) {
          
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_MS))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    public static String generateRefreshToken(String username) {

        String refreshToken = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_MS))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
        refreshTokens.put(refreshToken, username);
        return refreshToken;
    }

    public static String validateAccessToken(String token) {
        try {
            // Throws exception if not valid
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
            
            // Check whether token is expired
            if (claims.getExpiration().after(new Date()))
                return claims.getSubject();
            else    
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String validateRefreshToken(String token) {
        try {
            // Throws exception if not valid
            if (!refreshTokens.containsKey(token)) return null;
            
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

            // Check whether token is expired
            if (claims.getExpiration().after(new Date()))
                return claims.getSubject();
            else    
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void invalidateRefreshToken(String token) {
        refreshTokens.remove(token);
    }

    public static String validateTokenAndGetUsername(String token) {

        try {
            // Throws exception if not valid
            Claims claims = Jwts.parser()
                                .setSigningKey(SECRET_KEY)
                                .parseClaimsJws(token)
                                .getBody();

            // Check whether token is expired
            if (claims.getExpiration().after(new Date()))
                return claims.getSubject();
            else    
                return null;
            
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}