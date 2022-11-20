package com.vr.miniautorizador.exception;

public class BaseTransacaoException extends RuntimeException {
    public BaseTransacaoException(ErroDeTransacao erroDeTransacao) {
        super(erroDeTransacao.name());
    }
}
