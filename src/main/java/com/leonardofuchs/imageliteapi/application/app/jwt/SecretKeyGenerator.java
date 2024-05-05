package com.leonardofuchs.imageliteapi.application.app.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class SecretKeyGenerator { // Gerando chave de assinatura *caso for null

    private SecretKey key; // chave
    public SecretKey getKey(){ // Método que retorna a chave
        if (key == null) {
            key = Jwts.SIG.HS256.key().build(); // Se a chave for nula, gera uma nova chave(key)
        }
        return key;
    }
}
