package com.user.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // This is your static secret key. It must be the same for token generation and validation.
    private static final String SECRET = "a-string-secret-at-least-256-bits-long-change-this-please";

    // Generate a signing key from the string secret
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    /**
     * Generate a JWT token with extra claims and a subject (username).
     *
     * @param extraClaims additional claims like roles, permissions, etc.
     * @param username subject of the token (usually the user email or username)
     * @return signed JWT token string
     */
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))               // current time
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // valid for 1 day
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)                // use HS256 algorithm
                .compact();                                                     // generate compact string
    }

    /**
     * Extract the username (subject) from the JWT token.
     *
     * @param token JWT token string
     * @return username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generic method to extract any claim using a resolver function.
     *
     * @param token JWT token
     * @param resolver function to extract the claim
     * @return extracted claim value
     * @param <T> type of the claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }

    /**
     * Check if the token is valid for the given user.
     *
     * @param token JWT token
     * @param userDetails Spring Security's UserDetails object
     * @return true if valid and not expired
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Check if the token has expired.
     *
     * @param token JWT token
     * @return true if expired
     */
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}