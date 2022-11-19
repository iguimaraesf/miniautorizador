package com.vr.miniautorizador.fixture;

import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartaoValeFixture {
    public static CartaoNovoDto criarCartaoNovo() {
        return new CartaoNovoDto("1234123412341234", "1234");
    }
}
