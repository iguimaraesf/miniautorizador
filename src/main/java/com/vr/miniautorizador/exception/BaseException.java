package com.vr.miniautorizador.exception;

public abstract class BaseException extends RuntimeException {

    protected BaseException(String reason) {
        super(reason);
    }
}
