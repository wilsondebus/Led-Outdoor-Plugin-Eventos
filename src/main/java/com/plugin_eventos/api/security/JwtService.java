package com.plugin_eventos.api.security;

// cria e le tokens
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    // Em produção, essa chave deve vir de variável de ambiente, nunca fixa no código
    private final SecretKey chave = Keys.hmacShaKeyFor(
            "sua-chave-secreta-com-pelo-menos-32-caracteres!".getBytes()
    );

    public String gerarToken(UserDetails usuario) {
        return Jwts.builder()
                .subject(usuario.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(chave)
                .compact();
    }

    public String extrairEmail(String token) {
        return Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean tokenValido(String token, UserDetails usuario) {
        String email = extrairEmail(token);
        return email.equals(usuario.getUsername());
    }
}