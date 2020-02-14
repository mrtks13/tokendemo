package com.forsight.tokendemo.exception;

public class TokenDemoException extends RuntimeException {


    public TokenDemoException() {
        super("Invalid Token");
    }

    public TokenDemoException(String message) {
        super(message);
    }
}
