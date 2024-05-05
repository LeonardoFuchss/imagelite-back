package com.leonardofuchs.imageliteapi.application.app.jwt;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
