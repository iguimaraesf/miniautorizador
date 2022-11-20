package com.vr.miniautorizador.exception;

public class BaseTransacaoException extends BaseException {
    public BaseTransacaoException(ErroDeTransacao erroDeTransacao) {
        super(erroDeTransacao.name());
    }
}
