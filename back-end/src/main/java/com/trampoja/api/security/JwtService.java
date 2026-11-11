package com.trampoja.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey chave;
    private final long expiracaoMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expiracaoMs) {
        this.chave = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiracaoMs = expiracaoMs;
    }

    /**
     * @param email       e-mail do usuário (subject do token)
     * @param id          id do usuário
     * @param tipoUsuario "EMPRESA" ou "FREELANCER"
     */
    public String gerarToken(String email, Long id, String tipoUsuario) {
        Date agora = new Date();

        return Jwts.builder()
                .subject(email)
                .claim("id", id)
                .claim("tipo", tipoUsuario)
                .issuedAt(agora)
                .expiration(new Date(agora.getTime() + expiracaoMs))
                .signWith(chave)
                .compact();
    }
}
