package com.gustavodscruz.livranada.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAuthNotRetrivedException extends RuntimeException {
    public UserAuthNotRetrivedException() {
        super("User couldn't be retrieved.");
    }
}
