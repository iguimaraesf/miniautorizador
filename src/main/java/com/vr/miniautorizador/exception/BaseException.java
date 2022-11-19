package com.vr.miniautorizador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class BaseException extends ResponseStatusException {

    public BaseException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
