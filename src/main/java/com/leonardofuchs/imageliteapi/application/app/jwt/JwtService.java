package com.leonardofuchs.imageliteapi.application.app.jwt;

import com.leonardofuchs.imageliteapi.domain.AcessToken;
import com.leonardofuchs.imageliteapi.domain.entities.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKeyGenerator keyGenerator;

    public AcessToken generatedToken(User user) { // Gerando token com base no usuário

        SecretKey key = keyGenerator.getKey(); // Chamando a chave de assinatura criada no metodo SecretKeyGenerator
        var claims = GenerateTokenClaims(user);

        String token = Jwts // Gerando o token
                .builder()
                .signWith(key) // Assinatura da chave
                .subject(user.getEmail()) // identificador do usuário para quando decodificar o token
                .expiration(GenerateExpirationDate()) // Expiração do token criada no método GenerateExpirationDate
                .claims(claims) // Informações do token com base no usuário (criada no metodo generateTokenClaims)
                .compact();// Informações que queremos no nosso token.

        return new AcessToken(token);
    }

    private Date GenerateExpirationDate() {

        var expirationMinutes = 60; // Minutos de expiração do token
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutes); // Pegando o momento atual (now) e adicionando mais 60 minutos (GenerateExpirationDate)
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant()); // Transformando o LocalDateTime (now) em Date, passando o ZoneId systemDefault pra pegar o momento atual aonde esta localizado o meu servidor com toInstant!
    }

    private Map<String, Object> GenerateTokenClaims(User user) { // Método para retornar informações do token (claims)
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        return claims;
    }

    public String getEmailFromToken(String tokenJwt) { // Método que retorna o usuário a partir do token de forma decodificada
        try {
            return Jwts.parser()
                    .verifyWith(keyGenerator.getKey()) // Verificar a chave
                    .build()
                    .parseSignedClaims(tokenJwt) // Decodificar o token
                    .getPayload() // Retornar os claims
                    .getSubject();  // Retornar o subject (email) do token
        } catch (JwtException E){
         throw new InvalidTokenException(E.getMessage());
        }


    }
}
