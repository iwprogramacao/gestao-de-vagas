package br.com.rocketseat.main.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(String message) {
        super(message);
    }
}
