package com.vr.miniautorizador.fixture;

import com.vr.miniautorizador.repository.cartao.CartaoVale;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartaoValeFixture {
    public static CartaoNovoDto criarCartaoNovo() {
        return new CartaoNovoDto("1234123412341234", "1234");
    }

    public static CartaoVale criarEntidade() {
        var res = new CartaoVale();
        res.setNumeroCartao("1111222233334444");
        res.setSenha("$2a$16$b9Lb0fipDYfnV5s85/VKGOkdveqZV8CsF5PaOUKjbK69ZFIHBPUve");
        res.setSaldo(new BigDecimal("455.21"));
        return res;
    }
}
