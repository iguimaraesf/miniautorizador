package com.vr.miniautorizador.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    public BaseException(String reason) {
        super(reason);
    }
}
