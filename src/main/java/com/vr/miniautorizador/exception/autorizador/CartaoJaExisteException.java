package com.vr.miniautorizador.exception.autorizador;

import com.vr.miniautorizador.exception.BaseException;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import lombok.Getter;

@Getter
public class CartaoJaExisteException extends BaseException {
    private final CartaoNovoDto dto;

    public CartaoJaExisteException(CartaoNovoDto dto) {
        super("O cartão " + dto.getNumeroCartao() + " já existe");
        this.dto = dto;
    }
}
