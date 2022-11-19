package com.vr.miniautorizador.exception.handler;

import com.vr.miniautorizador.exception.autorizador.CartaoJaExisteException;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ValidationException;

@RestControllerAdvice
public class GerenciadorExcecao {
    @ExceptionHandler(CartaoJaExisteException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public CartaoNovoDto quandoJaExiste(CartaoJaExisteException ex, WebRequest request) {
        return ex.getDto();
    }
}
