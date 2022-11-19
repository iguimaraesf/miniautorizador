package com.vr.miniautorizador.exception.autorizador;

import com.vr.miniautorizador.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CartaoNaoEncontradoException extends BaseException {
    public CartaoNaoEncontradoException() {
        super("Cartão não encontrado");
    }
}
