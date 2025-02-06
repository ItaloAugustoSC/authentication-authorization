package com.italoasc.authentication_authorization.util;

import com.italoasc.authentication_authorization.model.LoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "mySecretKey";
    private String secret = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";

    public Date getJwtExpirationInMs() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60);
    }


    public String generateToken(LoginRequest login) {
        String jwtToken="";
        jwtToken = Jwts.builder()
                .setSubject(login.getUsername())
                .setIssuedAt(new Date())
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .compact();
        return jwtToken;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // Extrai o username a partir do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Valida se o token pertence ao usuário e se ainda não expirou
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Extrai qualquer informação do token (exemplo: username, data de expiração)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Verifica se o token já expirou
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Extrai todas as informações do token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Converte a chave secreta para um formato seguro
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

