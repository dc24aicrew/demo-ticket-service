package com.ticketmanagement.demo.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * Provider for JWT token generation and validation
 */
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secretKeyForDemoTicketServiceApplication}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h by default

    @Value("${security.jwt.token.header:Authorization}")
    private String authHeader;
    
    @Value("${security.jwt.token.prefix:Bearer}")
    private String tokenPrefix;

    private SecretKey key;
    
    @PostConstruct
    public void init() {
        // Use the raw string key instead of trying to decode it
        // Base64 encoding will be applied internally by the Keys.hmacShaKeyFor method
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Creates a JWT token for the provided authentication
     *
     * @param authentication the user authentication
     * @return the JWT token
     */
    public String createToken(Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Creates a JWT token for UserDetails
     * 
     * @param userDetails The user details object
     * @return The generated JWT token string
     */
    public String createToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
        
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Extracts the username from a token
     * 
     * @param token The JWT token
     * @return The username contained in the token
     */
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }
    
    /**
     * Creates an Authentication object from a token
     *
     * @param token the JWT token
     * @return the authentication
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    
    /**
     * Validates a token for expiration and signature validity
     *
     * @param token the JWT token
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token validation failed
            return false;
        }
    }
    
    /**
     * Extract claims from JWT token
     *
     * @param token the JWT token
     * @return the claims
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * Extracts the token from the authorization header
     * 
     * @param header The Authorization header value
     * @return The JWT token without the prefix
     */
    public String resolveToken(String header) {
        if (header != null && header.startsWith(tokenPrefix + " ")) {
            return header.substring(tokenPrefix.length() + 1);
        }
        return null;
    }
    
    /**
     * Gets the authorization header name
     * 
     * @return The header name for the JWT token
     */
    public String getAuthHeader() {
        return authHeader;
    }
    
    /**
     * Gets the token prefix
     * 
     * @return The prefix for the JWT token
     */
    public String getTokenPrefix() {
        return tokenPrefix;
    }
    
    /**
     * Gets the signing key
     * 
     * @return The secret key used for JWT signing
     */
    public SecretKey getKey() {
        return key;
    }
}
