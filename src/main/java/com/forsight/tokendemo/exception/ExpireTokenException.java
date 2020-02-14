package com.forsight.tokendemo.exception;

public class ExpireTokenException extends RuntimeException {


    public ExpireTokenException() {
        super("Token is expired");
    }

    public ExpireTokenException(String message) {
        super(message);
    }
}
