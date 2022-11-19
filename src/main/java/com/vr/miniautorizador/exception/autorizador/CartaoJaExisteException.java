package com.vr.miniautorizador.exception.autorizador;

import com.vr.miniautorizador.exception.BaseException;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartaoJaExisteException extends BaseException {
    private CartaoNovoDto dto;
    public CartaoJaExisteException(CartaoNovoDto dto) {
        super("O cartão " + dto.getNumeroCartao() + " já existe");
        this.dto = dto;
    }
}
