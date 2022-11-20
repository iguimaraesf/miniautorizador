package com.vr.miniautorizador.exception.handler;

import com.vr.miniautorizador.exception.BaseTransacaoException;
import com.vr.miniautorizador.exception.autorizador.CartaoJaExisteException;
import com.vr.miniautorizador.exception.autorizador.CartaoNaoEncontradoException;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GerenciadorExcecao {
    @ExceptionHandler(CartaoJaExisteException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public CartaoNovoDto quandoJaExiste(CartaoJaExisteException ex, WebRequest request) {
        return ex.getDto();
    }

    @ExceptionHandler(CartaoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String quandoNaoEncontrarCartao(CartaoNaoEncontradoException ex, WebRequest request) {
        return "";
    }

    @ExceptionHandler(BaseTransacaoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String quandoHouverErroNaTransacao(BaseTransacaoException ex, WebRequest request) {
        return ex.getMessage();
    }
}
