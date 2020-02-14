package com.forsight.tokendemo.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {


    public InvalidTokenException() {
        super("Invalid Token");
    }

    public InvalidTokenException(String message, Throwable throwable) { super(message);
    }
}
